package com.soa.soainventario.service;

import com.soa.soainventario.dto.request.DescuentoRequestDTO;
import com.soa.soainventario.dto.request.InsumoRequestDTO;
import com.soa.soainventario.dto.response.InsumoResponseDTO;
import com.soa.soainventario.dto.response.StockResponseDTO;
import com.soa.soainventario.entity.Insumo;
import com.soa.soainventario.entity.MovimientoInventario;
import com.soa.soainventario.exception.ResourceNotFoundException;
import com.soa.soainventario.repository.AlertaRepository;
import com.soa.soainventario.repository.InsumoRepository;
import com.soa.soainventario.repository.MovimientoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InsumoService {
    
    private final InsumoRepository insumoRepository;
    private final MovimientoRepository movimientoRepository;  // ← Agregar esta línea
    private final AlertaRepository alertaRepository;  // ← Si la necesitas

    public List<InsumoResponseDTO> listarTodos() {
        log.info("Listando todos los insumos");
        return insumoRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public InsumoResponseDTO buscarInsumo(UUID id) {
        log.info("Buscando insumo con ID: {}", id);
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + id));
        return convertToResponseDTO(insumo);
    }
    
    public Insumo buscarInsumoEntity(UUID id) {
        log.info("Buscando insumo entity con ID: {}", id);
        return insumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + id));
    }
    
    @Transactional
    public InsumoResponseDTO crearInsumo(InsumoRequestDTO request) {
        log.info("Creando nuevo insumo: {}", request.getNombre());
        
        Insumo insumo = new Insumo();
        insumo.setNombre(request.getNombre());
        insumo.setUnidadMedida(request.getUnidadMedida());
        insumo.setStockActual(0.0);
        insumo.setStockMinimo(request.getStockMinimo());
        insumo.setCostoPorUnidad(request.getCostoPorUnidad());
        insumo.setUbicacion(request.getUbicacion());
        
        Insumo savedInsumo = insumoRepository.save(insumo);
        log.info("Insumo creado exitosamente con ID: {}", savedInsumo.getId());
        
        return convertToResponseDTO(savedInsumo);
    }
    
    @Transactional
    public InsumoResponseDTO actualizarInsumo(UUID id, InsumoRequestDTO request) {
        log.info("Actualizando insumo con ID: {}", id);
        
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + id));
        
        insumo.setNombre(request.getNombre());
        insumo.setUnidadMedida(request.getUnidadMedida());
        insumo.setStockMinimo(request.getStockMinimo());
        insumo.setCostoPorUnidad(request.getCostoPorUnidad());
        insumo.setUbicacion(request.getUbicacion());
        
        Insumo updatedInsumo = insumoRepository.save(insumo);
        log.info("Insumo actualizado exitosamente");
        
        return convertToResponseDTO(updatedInsumo);
    }
    
    @Transactional
    public void eliminarInsumo(UUID id) {
        log.info("Eliminando insumo con ID: {}", id);
        
        if (!insumoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Insumo no encontrado con ID: " + id);
        }
        
        insumoRepository.deleteById(id);
        log.info("Insumo eliminado exitosamente");
    }
    
    @Transactional
    public InsumoResponseDTO actualizarStock(UUID id, double nuevaCantidad) {
        log.info("Actualizando stock del insumo {} a {}", id, nuevaCantidad);
        
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + id));
        
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        
        insumo.setStockActual(nuevaCantidad);
        Insumo updatedInsumo = insumoRepository.save(insumo);
        log.info("Stock actualizado exitosamente");
        
        return convertToResponseDTO(updatedInsumo);
    }
    
    @Transactional
    public InsumoResponseDTO incrementarStock(UUID id, double cantidad) {
        log.info("Incrementando stock del insumo {} en {}", id, cantidad);
        
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + id));
        
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a incrementar debe ser positiva");
        }
        
        double nuevoStock = insumo.getStockActual() + cantidad;
        insumo.setStockActual(nuevoStock);
        Insumo updatedInsumo = insumoRepository.save(insumo);
        
        return convertToResponseDTO(updatedInsumo);
    }
    
    @Transactional
    public InsumoResponseDTO decrementarStock(UUID id, double cantidad) {
        log.info("Decrementando stock del insumo {} en {}", id, cantidad);
        
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + id));
        
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a decrementar debe ser positiva");
        }
        
        double nuevoStock = insumo.getStockActual() - cantidad;
        if (nuevoStock < 0) {
            throw new IllegalArgumentException("Stock insuficiente");
        }
        
        insumo.setStockActual(nuevoStock);
        Insumo updatedInsumo = insumoRepository.save(insumo);
        
        return convertToResponseDTO(updatedInsumo);
    }
    
    private InsumoResponseDTO convertToResponseDTO(Insumo insumo) {
        // Calcular estado del stock
        String estadoStock = calcularEstadoStock(insumo.getStockActual(), insumo.getStockMinimo());
        
        return InsumoResponseDTO.builder()
                .id(insumo.getId())
                .nombre(insumo.getNombre())
                .unidadMedida(insumo.getUnidadMedida())
                .stockActual(insumo.getStockActual())
                .stockMinimo(insumo.getStockMinimo())
                .costoPorUnidad(insumo.getCostoPorUnidad())
                .ubicacion(insumo.getUbicacion())
                .estadoStock(estadoStock)
                .createdAt(insumo.getCreatedAt())
                .updatedAt(insumo.getUpdatedAt())
                .build();
    }
    
    private String calcularEstadoStock(double stockActual, double stockMinimo) {
        if (stockActual == 0) {
            return "SIN_STOCK";
        } else if (stockActual < stockMinimo) {
            return "BAJO_STOCK";
        } else if (stockActual < stockMinimo * 2) {
            return "STOCK_LIMITE";
        } else {
            return "NORMAL";
        }
    }

       @Transactional
    public StockResponseDTO descontarStock(DescuentoRequestDTO request) {
        // 1. Buscar el insumo
        Insumo insumo = insumoRepository.findById(request.getInsumoId())
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado"));
        
        // 2. Validar stock suficiente
        if (insumo.getStockActual() < request.getCantidad()) {
            throw new RuntimeException(String.format(
                "Stock insuficiente. Stock actual: %.2f %s, Requerido: %.2f %s",
                insumo.getStockActual(), insumo.getUnidadMedida(),
                request.getCantidad(), insumo.getUnidadMedida()
            ));
        }
        
        // 3. Calcular nuevo stock
        double nuevoStock = insumo.getStockActual() - request.getCantidad();
        insumo.setStockActual(nuevoStock);
        insumoRepository.save(insumo);
        
        // 4. Generar ordenId si no viene
        String ordenId = request.getOrdenId();
        if (ordenId == null) {
            ordenId = UUID.randomUUID().toString();
            log.info("OrdenId generado automáticamente: {}", ordenId);
        }
        
        // 5. Registrar movimiento de SALIDA
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setInsumo(insumo);
        movimiento.setTipo("SALIDA");
        movimiento.setCantidad(request.getCantidad());
        movimiento.setStockResultante(nuevoStock);
        movimiento.setMotivo("Produccion");
        movimiento.setUsuarioId(request.getUsuarioId());
        movimiento.setReferenciaId(ordenId);
        movimiento.setCreatedAt(LocalDateTime.now());
        movimientoRepository.save(movimiento);  // ← Ahora debería funcionar
        
        log.info("Movimiento registrado - OrdenId: {}, Cantidad: {}", ordenId, request.getCantidad());
        
        // 6. Retornar respuesta
        return StockResponseDTO.builder()
                .insumoId(insumo.getId())
                .nombre(insumo.getNombre())
                .stockActual(nuevoStock)
                .unidadMedida(insumo.getUnidadMedida())
                .requiereAlerta(nuevoStock < insumo.getStockMinimo())
                .build();
    }
    
    private void verificarYCrearAlerta(Insumo insumo) {
        if (insumo.getStockActual() < insumo.getStockMinimo()) {
            if (!alertaRepository.existsByInsumoIdAndEstado(insumo.getId(), "ACTIVA")) {
                // Crear alerta - esto dependerá de tu implementación de AlertaService
                log.warn("Stock bajo detectado para insumo: {}", insumo.getNombre());
            }
        }
    }
}