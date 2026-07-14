package com.soa.soainventario.controller;

import com.soa.soainventario.dto.request.MovimientoRequestDTO;
import com.soa.soainventario.dto.response.InsumoEstadisticasDTO;
import com.soa.soainventario.dto.response.MovimientoResponseDTO;
import com.soa.soainventario.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/movimientos")
@RequiredArgsConstructor
@Tag(name = "Movimientos", description = "API para gestión de movimientos de inventario")
public class MovimientoController {
    
    private final MovimientoService movimientoService;
    
    @PostMapping
    @Operation(summary = "Registrar movimiento de inventario")
      @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Movimiento registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<MovimientoResponseDTO> registrarMovimiento(@Valid @RequestBody MovimientoRequestDTO request) {
        MovimientoResponseDTO response = movimientoService.registrarMovimiento(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    // La ruta debe ser /movimientos/{id} (no /movimientos/insumo/{id})
    @GetMapping("/{insumoId}")
    @Operation(summary = "Historial de movimientos de un insumo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial de movimientos"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<List<MovimientoResponseDTO>> getHistorialByInsumo(@PathVariable UUID insumoId) {
        return ResponseEntity.ok(movimientoService.listarMovimientosPorInsumo(insumoId));
    }
    
    @GetMapping
    @Operation(summary = "Listar todos los movimientos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de movimientos"),
    })
    public ResponseEntity<List<MovimientoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(movimientoService.listarTodosMovimientos());
    }
    
    @GetMapping("/fechas")
    @Operation(summary = "Listar movimientos por rango de fechas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de movimientos"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<List<MovimientoResponseDTO>> listarPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(movimientoService.listarMovimientosPorFechas(desde, hasta));
    }
    
    @GetMapping("/resumen/{dias}")
    @Operation(summary = "Resumen de movimientos de los últimos N días")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resumen de movimientos"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<List<Object[]>> getResumen(@PathVariable int dias) {
        return ResponseEntity.ok(movimientoService.getResumenMovimientos(dias));
    }
    
    @GetMapping("/estadisticas/{insumoId}")
    @Operation(summary = "Estadísticas de un insumo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadísticas del insumo"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<InsumoEstadisticasDTO> getEstadisticas(@PathVariable UUID insumoId) {
        return ResponseEntity.ok(movimientoService.getEstadisticasInsumo(insumoId));
    }
}