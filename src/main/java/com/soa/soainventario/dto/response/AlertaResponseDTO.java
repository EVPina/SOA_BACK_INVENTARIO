package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder  // ← Esta anotación es necesaria
public class AlertaResponseDTO {
    @Schema(description = "ID de la alerta", example = "123e4567-e89b-12d3-a456-426614174004")
    private UUID id;
    @Schema(description = "ID del insumo", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID insumoId;
    @Schema(description = "Nombre del insumo", example = "Lechuga")
    private String insumoNombre;
    @Schema(description = "Nivel actual del insumo", example = "5.0")
    private double nivelActual;
    @Schema(description = "Nivel mínimo del insumo", example = "10.0")
    private double nivelMinimo;
    @Schema(description = "Unidad de medida", example = "kg")
    private String unidadMedida;
    @Schema(description = "Estado de la alerta", example = "PENDIENTE")
    private String estado;
    @Schema(description = "Fecha de creación de la alerta", example = "2023-01-01T10:00:00")
    private LocalDateTime creadaEn;
    @Schema(description = "Fecha de resolución de la alerta", example = "2023-01-01T11:00:00")
    private LocalDateTime resueltaEn;
}