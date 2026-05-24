package com.soa.soainventario.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soa.soainventario.dto.request.InsumoRequestDTO;
import com.soa.soainventario.dto.response.InsumoResponseDTO;
import com.soa.soainventario.entity.Insumo;
import com.soa.soainventario.exception.ResourceNotFoundException;
import com.soa.soainventario.repository.InsumoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InsumoService {
    
    private final InsumoRepository insumoRepository;
    
    public InsumoResponseDTO crearInsumo(InsumoRequestDTO request) {
        if (insumoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new RuntimeException("Ya existe un insumo con ese nombre");
        }
        
        Insumo insumo = new Insumo();
        insumo.setNombre(request.getNombre());
        insumo.setUnidadMedida(request.getUnidadMedida());
        insumo.setStockActual(BigDecimal.ZERO);
        insumo.setStockMinimo(request.getStockMinimo());
        insumo.setCostoPorUnidad(request.getCostoPorUnidad());
        insumo.setUbicacion(request.getUbicacion());
        
        insumo = insumoRepository.save(insumo);
        return mapToResponseDTO(insumo);
    }
    
    @Transactional(readOnly = true)
    public List<InsumoResponseDTO> listarTodos() {
        return insumoRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public InsumoResponseDTO buscarPorId(UUID id) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con id: " + id));
        return mapToResponseDTO(insumo);
    }
    
    @Transactional(readOnly = true)
    public List<InsumoResponseDTO> listarConStockBajo() {
        return insumoRepository.findInsumosConStockBajo().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    private InsumoResponseDTO mapToResponseDTO(Insumo insumo) {
        String estadoStock;
        if (insumo.getStockActual().compareTo(insumo.getStockMinimo()) < 0) {
            estadoStock = "BAJO";
        } else if (insumo.getStockActual().compareTo(insumo.getStockMinimo()) == 0) {
            estadoStock = "CRITICO";
        } else {
            estadoStock = "NORMAL";
        }
        
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
}