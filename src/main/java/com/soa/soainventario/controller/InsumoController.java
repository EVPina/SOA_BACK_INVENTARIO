package com.soa.soainventario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.soa.soainventario.dto.request.InsumoRequestDTO;
import com.soa.soainventario.dto.response.InsumoResponseDTO;
import com.soa.soainventario.service.InsumoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/insumos")
@RequiredArgsConstructor
@Tag(name = "Insumos", description = "API para gestión de insumos")
public class InsumoController {
    
    private final InsumoService insumoService;
    
    @PostMapping
    @Operation(summary = "Crear nuevo insumo")
    public ResponseEntity<InsumoResponseDTO> crearInsumo(@Valid @RequestBody InsumoRequestDTO request) {
        InsumoResponseDTO response = insumoService.crearInsumo(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos los insumos")
    public ResponseEntity<List<InsumoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(insumoService.listarTodos());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar insumo por ID")
    public ResponseEntity<InsumoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(insumoService.buscarPorId(id));
    }
    
    @GetMapping("/stock-bajo")
    @Operation(summary = "Listar insumos con stock bajo")
    public ResponseEntity<List<InsumoResponseDTO>> listarStockBajo() {
        return ResponseEntity.ok(insumoService.listarConStockBajo());
    }
}