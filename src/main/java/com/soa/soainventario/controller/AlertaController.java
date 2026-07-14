package com.soa.soainventario.controller;

import com.soa.soainventario.dto.response.AlertaResponseDTO;
import com.soa.soainventario.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de alertas activas"),
        @ApiResponse(responseCode = "204", description = "No hay alertas activas disponibles")
    })
    public ResponseEntity<List<AlertaResponseDTO>> listarAlertasActivas() {
        return ResponseEntity.ok(alertaService.listarAlertasActivas());
    }
    
    @PostMapping("/{insumoId}")
    @Operation(summary = "Crear alerta manual para un insumo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Alerta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<AlertaResponseDTO> crearAlerta(@PathVariable UUID insumoId) {
        return ResponseEntity.status(201).body(alertaService.crearAlerta(insumoId));
    }
    
    @PutMapping("/{alertaId}/resolver")
    @Operation(summary = "Resolver una alerta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alerta resuelta exitosamente"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    public ResponseEntity<Void> resolverAlerta(@PathVariable UUID alertaId) {
        alertaService.resolverAlerta(alertaId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/insumo/{insumoId}")
    @Operation(summary = "Resolver todas las alertas de un insumo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alertas resueltas exitosamente"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<Integer> resolverAlertasPorInsumo(@PathVariable UUID insumoId) {
        int resueltas = alertaService.resolverAlertasPorInsumo(insumoId);
        return ResponseEntity.ok(resueltas);
    }
    
    @GetMapping("/contador")
    @Operation(summary = "Contar alertas activas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteo de alertas activas")
    })
    public ResponseEntity<Long> contarAlertasActivas() {
        return ResponseEntity.ok(alertaService.contarAlertasActivas());
    }
    @GetMapping("/stock-bajo")
    @Operation(summary = "Obtener alertas de stock mínimo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de alertas de stock mínimo")
    })
    public ResponseEntity<List<AlertaResponseDTO>> listarAlertasStockBajo() {
        return ResponseEntity.ok(alertaService.listarAlertasStockBajo());
    }
}