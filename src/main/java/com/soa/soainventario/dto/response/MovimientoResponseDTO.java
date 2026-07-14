package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder  // ← Esta anotación es necesaria para usar builder()
public class MovimientoResponseDTO {

    @JsonProperty("id")
    @Schema(description = "ID del movimiento", example = "123e4567-e89b-12d3-a456-426614174003")
    private UUID id;
    @JsonProperty("insumoId")
    @Schema(description = "ID del insumo", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID insumoId;
    @JsonProperty("insumoNombre")
    @Schema(description = "Nombre del insumo", example = "Lechuga")
    private String insumoNombre;
    @JsonProperty("tipo")
    @Schema(description = "Tipo de movimiento", example = "ENTRADA")
    private String tipo;
    @JsonProperty("cantidad")
    @Schema(description = "Cantidad del movimiento", example = "5.0")
    private double cantidad;
    @JsonProperty("stockResultante")
    @Schema(description = "Stock resultante después del movimiento", example = "10.0")
    private double stockResultante;
    @JsonProperty("motivo")
    @Schema(description = "Motivo del movimiento", example = "Reposición de stock")
    private String motivo;
    @JsonProperty("usuarioId")
    @Schema(description = "ID del usuario que realiza el movimiento", example = "123e4567-e89b-12d3-a456-426614174002")
    private UUID usuarioId;
    @JsonProperty("referenciaId")
    @Schema(description = "ID de referencia del movimiento", example = "123e4567-e89b-12d3-a456-426614174003")
    private String referenciaId;
    @JsonProperty("createdAt")
    @Schema(description = "Fecha de creación del movimiento", example = "2023-01-01T10:00:00")
    private LocalDateTime createdAt;
}