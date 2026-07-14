package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class ConsumoInsumoResponseDTO {
    @Schema(description = "ID del consumo de insumo", example = "123e4567-e89b-12d3-a456-426614174003")
    private UUID id;
    @Schema(description = "ID del producto externo al que se le asignó el insumo", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID productoId;  // ID externo del producto
    @Schema(description = "ID del insumo", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID insumoId;
    @Schema(description = "Nombre del insumo", example = "Lechuga")
    private String insumoNombre;
    @Schema(description = "Unidad de medida", example = "kg")
    private String unidadMedida;
    @Schema(description = "Cantidad del insumo por porción", example = "2.5")
    private double cantidadPorPorcion;
    @Schema(description = "Costo unitario del insumo", example = "2.0")
    private double costoUnitario;
    @Schema(description = "Costo total del consumo", example = "5.0")
    private double costoTotal;
    @Schema(description = "Fecha de creación del consumo", example = "2023-01-01T10:00:00")
    private LocalDateTime createdAt;
}