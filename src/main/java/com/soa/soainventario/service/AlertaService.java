package com.soa.soainventario.service;

import com.soa.soainventario.dto.response.AlertaResponseDTO;
import com.soa.soainventario.entity.AlertaStock;
import com.soa.soainventario.entity.Insumo;
import com.soa.soainventario.exception.ResourceNotFoundException;
import com.soa.soainventario.repository.AlertaRepository;
import com.soa.soainventario.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertaService {
    
    private final AlertaRepository alertaRepository;
    private final InsumoRepository insumoRepository;
    
    // Crear alerta manualmente
    @Transactional
    public AlertaResponseDTO crearAlerta(UUID insumoId) {
        Insumo insumo = insumoRepository.findById(insumoId)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado"));
        
        // Verificar si ya existe alerta activa
        if (alertaRepository.existsByInsumoIdAndEstado(insumoId, "ACTIVA")) {
            throw new RuntimeException("Ya existe una alerta activa para este insumo");
        }
        
        AlertaStock alerta = new AlertaStock();
        alerta.setInsumo(insumo);
        alerta.setNivelActual(insumo.getStockActual());
        alerta.setNivelMinimo(insumo.getStockMinimo());
        alerta.setEstado("ACTIVA");
        
        alerta = alertaRepository.save(alerta);
        
        return mapToResponseDTO(alerta);
    }
    
    // Obtener todas las alertas activas
    @Transactional(readOnly = true)
    public List<AlertaResponseDTO> listarAlertasActivas() {
        return alertaRepository.findAlertasActivasWithInsumo().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Resolver alerta
    @Transactional
    public void resolverAlerta(UUID alertaId) {
        AlertaStock alerta = alertaRepository.findById(alertaId)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada"));
        
        alerta.setEstado("RESUELTA");
        alerta.setResueltaEn(LocalDateTime.now());
        alertaRepository.save(alerta);
    }
    
    // Resolver todas las alertas de un insumo
    @Transactional
    public int resolverAlertasPorInsumo(UUID insumoId) {
        return alertaRepository.resolverAlertasPorInsumo(insumoId, LocalDateTime.now());
    }
    
    // Contar alertas activas
    @Transactional(readOnly = true)
    public long contarAlertasActivas() {
        return alertaRepository.countAlertasActivas();
    }
    
    // Mapeo a DTO
    private AlertaResponseDTO mapToResponseDTO(AlertaStock alerta) {
        return AlertaResponseDTO.builder()
                .id(alerta.getId())
                .insumoId(alerta.getInsumo().getId())
                .insumoNombre(alerta.getInsumo().getNombre())
                .nivelActual(alerta.getNivelActual())
                .nivelMinimo(alerta.getNivelMinimo())
                .unidadMedida(alerta.getInsumo().getUnidadMedida())
                .estado(alerta.getEstado())
                .creadaEn(alerta.getCreadaEn())
                .resueltaEn(alerta.getResueltaEn())
                .build();
    }

    @Transactional(readOnly = true)
    public List<AlertaResponseDTO> listarAlertasStockBajo() {
        // Obtener insumos con stock actual < stock minimo
        List<Insumo> insumosConStockBajo = insumoRepository.findInsumosConStockBajo();
        
        // Para cada uno, crear o recuperar alerta activa
        return insumosConStockBajo.stream()
                .map(insumo -> {
                    // Buscar alerta activa existente
                    List<AlertaStock> alertas = alertaRepository.findByInsumoIdAndEstado(insumo.getId(), "ACTIVA");
                    if (!alertas.isEmpty()) {
                        return mapToResponseDTO(alertas.get(0));
                    }
                    // Crear nueva alerta
                    AlertaStock nuevaAlerta = new AlertaStock();
                    nuevaAlerta.setInsumo(insumo);
                    nuevaAlerta.setNivelActual(insumo.getStockActual());
                    nuevaAlerta.setNivelMinimo(insumo.getStockMinimo());
                    nuevaAlerta.setEstado("ACTIVA");
                    return mapToResponseDTO(alertaRepository.save(nuevaAlerta));
                })
                .collect(Collectors.toList());
    }
}