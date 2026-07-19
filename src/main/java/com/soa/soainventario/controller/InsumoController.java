package com.soa.soainventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soa.soainventario.dto.request.DescuentoRequestDTO;
import com.soa.soainventario.dto.request.InsumoRequestDTO;
import com.soa.soainventario.dto.response.InsumoResponseDTO;
import com.soa.soainventario.dto.response.StockResponseDTO;
import com.soa.soainventario.service.InsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@CrossOrigin(origins = "${app.frontend-url:http://localhost:4200}")
public class InsumoController {
    
    private final InsumoService insumoService;
    
    @GetMapping
    @Operation(summary = "Listar todos los insumos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de insumos"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Insumos no encontrados")
    })
    public ResponseEntity<List<InsumoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(insumoService.listarTodos());
    }
    
    @GetMapping("stock/{id}")
    @Operation(summary = "Obtener insumo por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Insumo encontrado"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<InsumoResponseDTO> buscarInsumo(@PathVariable UUID id) {
        return ResponseEntity.ok(insumoService.buscarInsumo(id));
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo insumo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Insumo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<InsumoResponseDTO> crearInsumo(@Valid @RequestBody InsumoRequestDTO request) {
        InsumoResponseDTO nuevoInsumo = insumoService.crearInsumo(request);
        return new ResponseEntity<>(nuevoInsumo, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar insumo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Insumo actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<InsumoResponseDTO> actualizarInsumo(
            @PathVariable UUID id,
            @Valid @RequestBody InsumoRequestDTO request) {
        InsumoResponseDTO insumoActualizado = insumoService.actualizarInsumo(id, request);
        return ResponseEntity.ok(insumoActualizado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar insumo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Insumo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<Void> eliminarInsumo(@PathVariable UUID id) {
        insumoService.eliminarInsumo(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/stock")
    @Operation(summary = "Actualizar stock del insumo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<InsumoResponseDTO> actualizarStock(
            @PathVariable UUID id,
            @RequestParam double cantidad) {
        InsumoResponseDTO insumoActualizado = insumoService.actualizarStock(id, cantidad);
        return ResponseEntity.ok(insumoActualizado);
    }
    
    @PatchMapping("/{id}/stock/incrementar")
    @Operation(summary = "Incrementar stock del insumo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock incrementado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<InsumoResponseDTO> incrementarStock(
            @PathVariable UUID id,
            @RequestParam double cantidad) {
        InsumoResponseDTO insumoActualizado = insumoService.incrementarStock(id, cantidad);
        return ResponseEntity.ok(insumoActualizado);
    }
    
    @PatchMapping("/{id}/stock/decrementar")
    @Operation(summary = "Decrementar stock del insumo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock decrementado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<InsumoResponseDTO> decrementarStock(
            @PathVariable UUID id,
            @RequestParam double cantidad) {
        InsumoResponseDTO insumoActualizado = insumoService.decrementarStock(id, cantidad);
        return ResponseEntity.ok(insumoActualizado);
    }

      @PutMapping("/descontar")
    @Operation(summary = "Descontar stock al marcar LISTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock descontado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<StockResponseDTO> descontarStock(@Valid @RequestBody DescuentoRequestDTO request) {
        StockResponseDTO response = insumoService.descontarStock(request);
    // public DescuentoRequestDTO crearInsumo(@RequestBody DescuentoRequestDTO request) {
    //     return request;
        return ResponseEntity.ok(response);
    }
    
}