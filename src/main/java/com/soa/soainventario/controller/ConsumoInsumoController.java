package com.soa.soainventario.controller;

import com.soa.soainventario.dto.request.ConsumoInsumoRequestDTO;
import com.soa.soainventario.dto.response.ConsumoInsumoResponseDTO;
import com.soa.soainventario.service.ConsumoInsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/consumo-insumos")
@RequiredArgsConstructor
@Tag(name = "Consumo Insumos", description = "API para gestión de recetas de productos (productos externos)")
public class ConsumoInsumoController {
    
    private final ConsumoInsumoService consumoInsumoService;
    
    @PostMapping
    @Operation(summary = "Asignar insumo a producto externo")
    public ResponseEntity<ConsumoInsumoResponseDTO> asignarInsumo(@Valid @RequestBody ConsumoInsumoRequestDTO request) {
        return new ResponseEntity<>(consumoInsumoService.asignarInsumoAProducto(request), HttpStatus.CREATED);
    }
    
    @PutMapping("/{consumoId}")
    @Operation(summary = "Actualizar cantidad de consumo")
    public ResponseEntity<ConsumoInsumoResponseDTO> actualizarConsumo(
            @PathVariable UUID consumoId,
            @RequestParam double cantidad) {
        return ResponseEntity.ok(consumoInsumoService.actualizarConsumo(consumoId, cantidad));
    }
    
    @DeleteMapping("/{consumoId}")
    @Operation(summary = "Eliminar consumo de insumo")
    public ResponseEntity<Void> eliminarConsumo(@PathVariable UUID consumoId) {
        consumoInsumoService.eliminarConsumo(consumoId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/producto/{productoId}")
    @Operation(summary = "Eliminar todos los consumos de un producto")
    public ResponseEntity<Void> eliminarConsumosPorProducto(@PathVariable UUID productoId) {
        consumoInsumoService.eliminarConsumosPorProducto(productoId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Listar consumos por producto externo")
    public ResponseEntity<List<ConsumoInsumoResponseDTO>> listarPorProducto(@PathVariable UUID productoId) {
        return ResponseEntity.ok(consumoInsumoService.listarConsumosPorProducto(productoId));
    }
    
    @GetMapping("/producto/{productoId}/costo")
    @Operation(summary = "Calcular costo de producción de un producto")
    public ResponseEntity<Double> calcularCostoProduccion(@PathVariable UUID productoId) {
        return ResponseEntity.ok(consumoInsumoService.calcularCostoProduccion(productoId));
    }
}