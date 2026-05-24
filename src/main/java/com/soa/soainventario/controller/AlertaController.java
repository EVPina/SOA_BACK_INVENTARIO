package com.soa.soainventario.controller;

import com.soa.soainventario.dto.response.AlertaResponseDTO;
import com.soa.soainventario.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/alertas")
@RequiredArgsConstructor
@Tag(name = "Alertas", description = "API para gestión de alertas de stock")
public class AlertaController {
    
    private final AlertaService alertaService;
    
    @GetMapping("/activas")
    @Operation(summary = "Listar todas las alertas activas")
    public ResponseEntity<List<AlertaResponseDTO>> listarAlertasActivas() {
        return ResponseEntity.ok(alertaService.listarAlertasActivas());
    }
    
    @PostMapping("/{insumoId}")
    @Operation(summary = "Crear alerta manual para un insumo")
    public ResponseEntity<AlertaResponseDTO> crearAlerta(@PathVariable UUID insumoId) {
        return ResponseEntity.ok(alertaService.crearAlerta(insumoId));
    }
    
    @PutMapping("/{alertaId}/resolver")
    @Operation(summary = "Resolver una alerta")
    public ResponseEntity<Void> resolverAlerta(@PathVariable UUID alertaId) {
        alertaService.resolverAlerta(alertaId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/insumo/{insumoId}")
    @Operation(summary = "Resolver todas las alertas de un insumo")
    public ResponseEntity<Integer> resolverAlertasPorInsumo(@PathVariable UUID insumoId) {
        int resueltas = alertaService.resolverAlertasPorInsumo(insumoId);
        return ResponseEntity.ok(resueltas);
    }
    
    @GetMapping("/contador")
    @Operation(summary = "Contar alertas activas")
    public ResponseEntity<Long> contarAlertasActivas() {
        return ResponseEntity.ok(alertaService.contarAlertasActivas());
    }
}