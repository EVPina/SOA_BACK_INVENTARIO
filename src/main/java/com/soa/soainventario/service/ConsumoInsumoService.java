package com.soa.soainventario.service;

import com.soa.soainventario.dto.request.ConsumoInsumoRequestDTO;
import com.soa.soainventario.dto.response.ConsumoInsumoResponseDTO;
import com.soa.soainventario.entity.ConsumoInsumo;
import com.soa.soainventario.entity.Insumo;
import com.soa.soainventario.exception.ResourceNotFoundException;
import com.soa.soainventario.repository.ConsumoInsumoRepository;
import com.soa.soainventario.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsumoInsumoService {
    
    private final ConsumoInsumoRepository consumoInsumoRepository;
    private final InsumoRepository insumoRepository;
    
    // Asignar insumo a producto
    @Transactional
    public ConsumoInsumoResponseDTO asignarInsumoAProducto(ConsumoInsumoRequestDTO request) {
        // Verificar si ya existe la relación
        if (consumoInsumoRepository.existsByProductoIdAndInsumoId(request.getProductoId(), request.getInsumoId())) {
            throw new RuntimeException("Este insumo ya está asignado a este producto");
        }
        
        // Verificar que el insumo existe
        Insumo insumo = insumoRepository.findById(request.getInsumoId())
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado"));
        
        // Crear el consumo
        ConsumoInsumo consumo = new ConsumoInsumo();
        consumo.setProductoId(request.getProductoId());
        consumo.setInsumo(insumo);
        consumo.setCantidadPorPorcion(request.getCantidadPorPorcion());
        
        consumo = consumoInsumoRepository.save(consumo);
        
        return mapToResponseDTO(consumo);
    }
    
    // Actualizar cantidad de consumo
    @Transactional
    public ConsumoInsumoResponseDTO actualizarConsumo(UUID consumoId, double nuevaCantidad) {
        ConsumoInsumo consumo = consumoInsumoRepository.findById(consumoId)
                .orElseThrow(() -> new ResourceNotFoundException("Consumo no encontrado"));
        
        consumo.setCantidadPorPorcion(nuevaCantidad);
        consumo = consumoInsumoRepository.save(consumo);
        
        return mapToResponseDTO(consumo);
    }
    
    // Eliminar consumo
    @Transactional
    public void eliminarConsumo(UUID consumoId) {
        ConsumoInsumo consumo = consumoInsumoRepository.findById(consumoId)
                .orElseThrow(() -> new ResourceNotFoundException("Consumo no encontrado"));
        
        consumoInsumoRepository.delete(consumo);
    }
    
    // Eliminar todos los consumos de un producto
    @Transactional
    public void eliminarConsumosPorProducto(UUID productoId) {
        consumoInsumoRepository.deleteByProductoId(productoId);
    }
    
    // Listar consumos por producto
    @Transactional(readOnly = true)
    public List<ConsumoInsumoResponseDTO> listarConsumosPorProducto(UUID productoId) {
        return consumoInsumoRepository.findConsumosWithInsumoByProductoId(productoId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Calcular costo de producción de un producto
    @Transactional(readOnly = true)
    public double calcularCostoProduccion(UUID productoId) {
        double costo = consumoInsumoRepository.calcularCostoProduccion(productoId);
        return costo;}
    
    // Obtener resumen de insumos por producto
    @Transactional(readOnly = true)
    public List<Object[]> getResumenInsumosPorProducto(UUID productoId) {
        return consumoInsumoRepository.getResumenInsumosPorProducto(productoId);
    }
    
    // Mapeo a DTO
    private ConsumoInsumoResponseDTO mapToResponseDTO(ConsumoInsumo consumo) {
        return ConsumoInsumoResponseDTO.builder()
                .id(consumo.getId())
                .productoId(consumo.getProductoId())
                .insumoId(consumo.getInsumo().getId())
                .insumoNombre(consumo.getInsumo().getNombre())
                .unidadMedida(consumo.getInsumo().getUnidadMedida())
                .cantidadPorPorcion(consumo.getCantidadPorPorcion())
                .costoUnitario(consumo.getInsumo().getCostoPorUnidad())
                .costoTotal(consumo.getCantidadPorPorcion() * consumo.getInsumo().getCostoPorUnidad())
                .createdAt(consumo.getCreatedAt())
                .build();
    }

    // En InsumoService.java

    @Transactional(readOnly = true)
    public Insumo buscarInsumoEntity(UUID id) {
        return insumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con id: " + id));
    }
}