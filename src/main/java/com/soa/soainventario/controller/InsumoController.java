package com.soa.soainventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soa.soainventario.dto.request.DescuentoRequestDTO;
import com.soa.soainventario.dto.request.InsumoRequestDTO;
import com.soa.soainventario.dto.response.InsumoResponseDTO;
import com.soa.soainventario.dto.response.StockResponseDTO;
import com.soa.soainventario.service.InsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/insumos")
@RequiredArgsConstructor
@Tag(name = "Insumos", description = "API para gestionar insumos del inventario")
@CrossOrigin(origins = "*")
public class InsumoController {
    
    private final InsumoService insumoService;
    
    @GetMapping
    @Operation(summary = "Listar todos los insumos")
    public ResponseEntity<List<InsumoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(insumoService.listarTodos());
    }
    
    @GetMapping("stock/{id}")
    @Operation(summary = "Obtener insumo por ID")
    public ResponseEntity<InsumoResponseDTO> buscarInsumo(@PathVariable UUID id) {
        return ResponseEntity.ok(insumoService.buscarInsumo(id));
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo insumo")
    public ResponseEntity<InsumoResponseDTO> crearInsumo(@Valid @RequestBody InsumoRequestDTO request) {
        InsumoResponseDTO nuevoInsumo = insumoService.crearInsumo(request);
        return new ResponseEntity<>(nuevoInsumo, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar insumo")
    public ResponseEntity<InsumoResponseDTO> actualizarInsumo(
            @PathVariable UUID id,
            @Valid @RequestBody InsumoRequestDTO request) {
        InsumoResponseDTO insumoActualizado = insumoService.actualizarInsumo(id, request);
        return ResponseEntity.ok(insumoActualizado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar insumo")
    public ResponseEntity<Void> eliminarInsumo(@PathVariable UUID id) {
        insumoService.eliminarInsumo(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/stock")
    @Operation(summary = "Actualizar stock del insumo")
    public ResponseEntity<InsumoResponseDTO> actualizarStock(
            @PathVariable UUID id,
            @RequestParam double cantidad) {
        InsumoResponseDTO insumoActualizado = insumoService.actualizarStock(id, cantidad);
        return ResponseEntity.ok(insumoActualizado);
    }
    
    @PatchMapping("/{id}/stock/incrementar")
    @Operation(summary = "Incrementar stock del insumo")
    public ResponseEntity<InsumoResponseDTO> incrementarStock(
            @PathVariable UUID id,
            @RequestParam double cantidad) {
        InsumoResponseDTO insumoActualizado = insumoService.incrementarStock(id, cantidad);
        return ResponseEntity.ok(insumoActualizado);
    }
    
    @PatchMapping("/{id}/stock/decrementar")
    @Operation(summary = "Decrementar stock del insumo")
    public ResponseEntity<InsumoResponseDTO> decrementarStock(
            @PathVariable UUID id,
            @RequestParam double cantidad) {
        InsumoResponseDTO insumoActualizado = insumoService.decrementarStock(id, cantidad);
        return ResponseEntity.ok(insumoActualizado);
    }

      @PutMapping("/descontar")
    @Operation(summary = "Descontar stock al marcar LISTO")
    public ResponseEntity<StockResponseDTO> descontarStock(@Valid @RequestBody DescuentoRequestDTO request) {
        StockResponseDTO response = insumoService.descontarStock(request);
    // public DescuentoRequestDTO crearInsumo(@RequestBody DescuentoRequestDTO request) {
    //     return request;
        return ResponseEntity.ok(response);
    }
    
}