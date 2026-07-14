package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class InsumoResponseDTO {
    @Schema(description = "ID del insumo", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID id;
    @Schema(description = "Nombre del insumo", example = "Lechuga")
    private String nombre;
    @Schema(description = "Unidad de medida", example = "kg")
    private String unidadMedida;
    @Schema(description = "Stock actual del insumo", example = "10.0")
    private double stockActual;
    @Schema(description = "Stock mínimo del insumo", example = "5.0")
    private double stockMinimo;
    @Schema(description = "Costo por unidad", example = "2.0")
    private double costoPorUnidad;
    @Schema(description = "Ubicación del insumo", example = "Bodega A")
    private String ubicacion;
    @Schema(description = "Estado del stock", example = "DISPONIBLE")
    private String estadoStock;
    @Schema(description = "Fecha de creación", example = "2023-01-01T10:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "Fecha de última actualización", example = "2023-01-01T10:00:00")
    private LocalDateTime updatedAt;
}