package com.soa.soainventario.service;

import com.soa.soainventario.dto.request.ProduccionRequestDTO;
import com.soa.soainventario.dto.response.ConsumoDetalleDTO;
import com.soa.soainventario.dto.response.ProduccionResponseDTO;
import com.soa.soainventario.entity.Insumo;
import com.soa.soainventario.entity.MovimientoInventario;
import com.soa.soainventario.repository.ConsumoInsumoRepository;
import com.soa.soainventario.repository.InsumoRepository;
import com.soa.soainventario.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProduccionService {
    
    private final ConsumoInsumoRepository consumoInsumoRepository;
    private final InsumoRepository insumoRepository;
    private final MovimientoRepository movimientoRepository;
    
    // Producir producto
    @Transactional
    public ProduccionResponseDTO producirProducto(ProduccionRequestDTO request) {
        double cantidadProductos = Double.valueOf(request.getCantidad());
        
        // Obtener insumos necesarios
        List<Object[]> insumosNecesarios = consumoInsumoRepository.verificarStockParaProduccion(
                request.getProductoId(), 
                cantidadProductos
        );
        
        if (insumosNecesarios.isEmpty()) {
            return ProduccionResponseDTO.builder()
                    .productoId(request.getProductoId())
                    .cantidadProducida(0)
                    .costoTotalProduccion(0.0)
                    .insumosConsumidos(new ArrayList<>())
                    .exito(false)
                    .mensaje("El producto no tiene insumos definidos en su receta")
                    .build();
        }
        
        List<ConsumoDetalleDTO> detalles = new ArrayList<>();
        boolean stockSuficiente = true;
        double costoTotalProduccion = 0.0;
        
        // Verificar stock y preparar detalles
        for (Object[] insumo : insumosNecesarios) {
            UUID insumoId = (UUID) insumo[0];
            String insumoNombre = (String) insumo[1];
            double stockActual = (Double) insumo[2];
            int cantidadNecesaria = (int) insumo[4];
            
            Insumo insumoEntity = insumoRepository.findById(insumoId).orElse(null);
            double costoUnitario = insumoEntity != null ? insumoEntity.getCostoPorUnidad() : 0.0;
            double costoTotalInsumo = insumoEntity != null ? insumoEntity.getCostoPorUnidad() * cantidadNecesaria : 0.0;
            
            boolean suficiente = stockActual >= cantidadNecesaria;
            if (!suficiente) stockSuficiente = false;
            
            costoTotalProduccion += costoTotalInsumo;
            
            ConsumoDetalleDTO detalle = ConsumoDetalleDTO.builder()
                    .insumoId(insumoId)
                    .insumoNombre(insumoNombre)
                    .cantidadConsumida(cantidadNecesaria)
                    .stockAntes(stockActual)
                    .stockDespues(suficiente ? stockActual - cantidadNecesaria : stockActual)
                    .costoUnitario(costoUnitario)
                    .costoTotal(costoTotalInsumo)
                    .suficiente(suficiente)
                    .build();
            
            detalles.add(detalle);
        }
        
        // Si hay stock suficiente, realizar producción
        if (stockSuficiente) {
            for (ConsumoDetalleDTO detalle : detalles) {
                Insumo insumo = insumoRepository.findById(detalle.getInsumoId())
                        .orElseThrow(() -> new RuntimeException("Insumo no encontrado: " + detalle.getInsumoId()));
                
                // Actualizar stock
                double nuevoStock = insumo.getStockActual() - detalle.getCantidadConsumida();
                insumo.setStockActual(nuevoStock);
                insumoRepository.save(insumo);
                
                // Registrar movimiento de salida
                MovimientoInventario movimiento = new MovimientoInventario();
                movimiento.setInsumo(insumo);
                movimiento.setTipo("SALIDA");
                movimiento.setCantidad(detalle.getCantidadConsumida());
                movimiento.setStockResultante(nuevoStock);
                movimiento.setMotivo("Produccion");
                movimiento.setUsuarioId(request.getUsuarioId());
                movimiento.setReferenciaId(request.getProductoId().toString()); // Referencia al producto externo
                movimiento.setCreatedAt(LocalDateTime.now());
                movimientoRepository.save(movimiento);
            }
        }
        
        return ProduccionResponseDTO.builder()
                .productoId(request.getProductoId())
                .cantidadProducida(stockSuficiente ? request.getCantidad() : 0)
                .costoTotalProduccion(costoTotalProduccion)
                .insumosConsumidos(detalles)
                .exito(stockSuficiente)
                .mensaje(stockSuficiente ? 
                        String.format("Producción exitosa. Se produjeron %d unidades. Costo total: S/ %.2f", 
                                request.getCantidad(), costoTotalProduccion) :
                        "Stock insuficiente para producir " + request.getCantidad() + " unidades")
                .build();
    }
    
    // Verificar disponibilidad
    @Transactional(readOnly = true)
    public ProduccionResponseDTO verificarDisponibilidad(ProduccionRequestDTO request) {
        double cantidadProductos = request.getCantidad();
        
        List<Object[]> insumosNecesarios = consumoInsumoRepository.verificarStockParaProduccion(
                request.getProductoId(), 
                cantidadProductos
        );
        
        List<ConsumoDetalleDTO> detalles = new ArrayList<>();
        boolean stockSuficiente = true;
        double costoTotalProduccion = 0.0;
        
        for (Object[] insumo : insumosNecesarios) {
            UUID insumoId = (UUID) insumo[0];
            String insumoNombre = (String) insumo[1];
            double stockActual = (double) insumo[2];
            double cantidadNecesaria = (double) insumo[4];
            
            Insumo insumoEntity = insumoRepository.findById(insumoId).orElse(null);
            double costoUnitario = insumoEntity != null ? insumoEntity.getCostoPorUnidad() : 0.0;
            double costoTotalInsumo = cantidadNecesaria * costoUnitario;
            
            boolean suficiente = stockActual >= cantidadNecesaria;
            if (!suficiente) stockSuficiente = false;
            costoTotalProduccion += costoTotalInsumo;
            
            ConsumoDetalleDTO detalle = ConsumoDetalleDTO.builder()
                    .insumoId(insumoId)
                    .insumoNombre(insumoNombre)
                    .cantidadConsumida(cantidadNecesaria)
                    .stockAntes(stockActual)
                    .costoUnitario(costoUnitario)
                    .costoTotal(costoTotalInsumo)
                    .suficiente(suficiente)
                    .build();
            
            detalles.add(detalle);
        }
        
        return ProduccionResponseDTO.builder()
                .productoId(request.getProductoId())
                .cantidadProducida(request.getCantidad())
                .costoTotalProduccion(costoTotalProduccion)
                .insumosConsumidos(detalles)
                .exito(stockSuficiente)
                .mensaje(stockSuficiente ? 
                        "Hay suficiente stock para producir " + request.getCantidad() + " unidades" :
                        "No hay suficiente stock para producir " + request.getCantidad() + " unidades")
                .build();
    }
    
    // Obtener productos que usan un insumo específico
    @Transactional(readOnly = true)
    public List<UUID> getProductosByInsumo(UUID insumoId) {
        return consumoInsumoRepository.findProductosByInsumoId(insumoId);
    }
}