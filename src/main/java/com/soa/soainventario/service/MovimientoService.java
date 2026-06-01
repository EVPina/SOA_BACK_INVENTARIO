package com.soa.soainventario.service;

import com.soa.soainventario.dto.request.MovimientoRequestDTO;
import com.soa.soainventario.dto.response.InsumoEstadisticasDTO;
import com.soa.soainventario.dto.response.MovimientoResponseDTO;
import com.soa.soainventario.entity.Insumo;
import com.soa.soainventario.entity.MovimientoInventario;
import com.soa.soainventario.exception.ResourceNotFoundException;
import com.soa.soainventario.repository.InsumoRepository;
import com.soa.soainventario.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovimientoService {
    
    private final MovimientoRepository movimientoRepository;
    private final InsumoRepository insumoRepository;
    
    // Registrar movimiento
    @Transactional
    public MovimientoResponseDTO registrarMovimiento(MovimientoRequestDTO request) {
        // Validar insumo
        Insumo insumo = insumoRepository.findById(request.getInsumoId())
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con id: " + request.getInsumoId()));
        
        double stockActual = insumo.getStockActual();
        double nuevoStock;
        
        // Calcular nuevo stock según tipo de movimiento
        switch (request.getTipo()) {
            case "ENTRADA":
                nuevoStock = stockActual + request.getCantidad();
                break;
            case "SALIDA":
                if (stockActual < request.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente. Stock actual: " + stockActual + 
                                               ", Cantidad solicitada: " + request.getCantidad());
                }
                nuevoStock = stockActual - request.getCantidad();
                break;
            case "AJUSTE":
                nuevoStock = request.getCantidad();
                break;
            default:
                throw new RuntimeException("Tipo de movimiento inválido: " + request.getTipo());
        }
        
        // Actualizar stock del insumo (el trigger de alerta se ejecutará automáticamente)
        insumo.setStockActual(nuevoStock);
        insumoRepository.save(insumo);
        
        // Registrar movimiento
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setInsumo(insumo);
        movimiento.setTipo(request.getTipo());
        movimiento.setCantidad(request.getCantidad());
        movimiento.setStockResultante(nuevoStock);
        movimiento.setMotivo(request.getMotivo());
        movimiento.setUsuarioId(request.getUsuarioId());
        movimiento.setReferenciaId(request.getReferenciaId());
        
        movimiento = movimientoRepository.save(movimiento);
        
        return mapToResponseDTO(movimiento);
    }
    
    // Listar movimientos por insumo
    @Transactional(readOnly = true)
    public List<MovimientoResponseDTO> listarMovimientosPorInsumo(UUID insumoId) {
        // Verificar que el insumo existe
        if (!insumoRepository.existsById(insumoId)) {
            throw new ResourceNotFoundException("Insumo no encontrado con id: " + insumoId);
        }
        
        return movimientoRepository.findByInsumoIdOrderByCreatedAtDesc(insumoId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Listar todos los movimientos
    @Transactional(readOnly = true)
    public List<MovimientoResponseDTO> listarTodosMovimientos() {
        return movimientoRepository.findUltimosMovimientos().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Listar movimientos por rango de fechas
    @Transactional(readOnly = true)
    public List<MovimientoResponseDTO> listarMovimientosPorFechas(LocalDateTime desde, LocalDateTime hasta) {
        return movimientoRepository.findByCreatedAtBetween(desde, hasta).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener resumen de movimientos
    @Transactional(readOnly = true)
    public List<Object[]> getResumenMovimientos(int dias) {
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(dias);
        return movimientoRepository.findResumenMovimientosPorDia(fechaInicio);
    }
    
    // Obtener estadísticas de insumo
    @Transactional(readOnly = true)
    public InsumoEstadisticasDTO getEstadisticasInsumo(UUID insumoId) {
        double totalEntradas = movimientoRepository.sumEntradasByInsumo(insumoId);
        double totalSalidas = movimientoRepository.sumSalidasByInsumo(insumoId);
        
        return InsumoEstadisticasDTO.builder()
                .insumoId(insumoId)
                .totalEntradas(totalEntradas)
                .totalSalidas(totalSalidas)
                .saldoActual(totalEntradas - totalSalidas)
                .build();
    }
    
    // Mapeo a DTO
    private MovimientoResponseDTO mapToResponseDTO(MovimientoInventario movimiento) {
        return MovimientoResponseDTO.builder()
                .id(movimiento.getId())
                .insumoId(movimiento.getInsumo().getId())
                .insumoNombre(movimiento.getInsumo().getNombre())
                .tipo(movimiento.getTipo())
                .cantidad(movimiento.getCantidad())
                .stockResultante(movimiento.getStockResultante())
                .motivo(movimiento.getMotivo())
                .usuarioId(movimiento.getUsuarioId())
                .referenciaId(movimiento.getReferenciaId())
                .createdAt(movimiento.getCreatedAt())
                .build();
    }
}