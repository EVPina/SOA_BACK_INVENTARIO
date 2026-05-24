package com.soa.soainventario.controller;

import com.soa.soainventario.dto.request.MovimientoRequestDTO;
import com.soa.soainventario.dto.response.InsumoEstadisticasDTO;
import com.soa.soainventario.dto.response.MovimientoResponseDTO;
import com.soa.soainventario.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<MovimientoResponseDTO> registrarMovimiento(@Valid @RequestBody MovimientoRequestDTO request) {
        MovimientoResponseDTO response = movimientoService.registrarMovimiento(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/insumo/{insumoId}")
    @Operation(summary = "Listar movimientos por insumo")
    public ResponseEntity<List<MovimientoResponseDTO>> listarMovimientosPorInsumo(@PathVariable UUID insumoId) {
        return ResponseEntity.ok(movimientoService.listarMovimientosPorInsumo(insumoId));
    }
    
    @GetMapping
    @Operation(summary = "Listar todos los movimientos")
    public ResponseEntity<List<MovimientoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(movimientoService.listarTodosMovimientos());
    }
    
    @GetMapping("/fechas")
    @Operation(summary = "Listar movimientos por rango de fechas")
    public ResponseEntity<List<MovimientoResponseDTO>> listarPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(movimientoService.listarMovimientosPorFechas(desde, hasta));
    }
    
    @GetMapping("/resumen/{dias}")
    @Operation(summary = "Resumen de movimientos de los últimos N días")
    public ResponseEntity<List<Object[]>> getResumen(@PathVariable int dias) {
        return ResponseEntity.ok(movimientoService.getResumenMovimientos(dias));
    }
    
    @GetMapping("/estadisticas/{insumoId}")
    @Operation(summary = "Estadísticas de un insumo")
    public ResponseEntity<InsumoEstadisticasDTO> getEstadisticas(@PathVariable UUID insumoId) {
        return ResponseEntity.ok(movimientoService.getEstadisticasInsumo(insumoId));
    }
}