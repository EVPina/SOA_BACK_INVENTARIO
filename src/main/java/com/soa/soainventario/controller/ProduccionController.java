package com.soa.soainventario.controller;

import com.soa.soainventario.dto.request.ProduccionRequestDTO;
import com.soa.soainventario.dto.response.ProduccionResponseDTO;
import com.soa.soainventario.service.ProduccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/produccion")
@RequiredArgsConstructor
@Tag(name = "Producción", description = "API para gestión de producción usando productos externos")
public class ProduccionController {
    
    private final ProduccionService produccionService;
    
    @PostMapping("/producir")
    @Operation(summary = "Producir un producto (consume insumos del inventario)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto producido exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<ProduccionResponseDTO> producir(@Valid @RequestBody ProduccionRequestDTO request) {
        return ResponseEntity.ok(produccionService.producirProducto(request));
    }
    
    @PostMapping("/verificar")
    @Operation(summary = "Verificar si hay stock suficiente para producir")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Disponibilidad verificada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<ProduccionResponseDTO> verificar(@Valid @RequestBody ProduccionRequestDTO request) {
        return ResponseEntity.ok(produccionService.verificarDisponibilidad(request));
    }
    
    @GetMapping("/insumo/{insumoId}/productos")
    @Operation(summary = "Obtener productos que usan un insumo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<List<UUID>> getProductosByInsumo(@PathVariable UUID insumoId) {
        return ResponseEntity.ok(produccionService.getProductosByInsumo(insumoId));
    }
}