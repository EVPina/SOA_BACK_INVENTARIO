package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class MovimientoRequestDTO {
    
    @Schema(description = "ID del insumo", required = true, example = "123e4567-e89b-12d3-a456-426614174001")
    @JsonProperty("insumoId")
    @NotNull(message = "El ID del insumo es obligatorio")
    private UUID insumoId;
    
    @Schema(description = "Tipo de movimiento", required = true, example = "ENTRADA")
    @JsonProperty("tipo")
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "ENTRADA|SALIDA|AJUSTE", message = "Tipo de movimiento inválido")
    private String tipo;
    
    @Schema(description = "Cantidad del movimiento", required = true, example = "2.5")
    @JsonProperty("cantidad")
    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0")
    private double cantidad;
    
    @Schema(description = "Motivo del movimiento", required = true, example = "Produccion")
    @JsonProperty("motivo")
    @NotBlank(message = "El motivo es obligatorio")
    @Pattern(regexp = "Produccion|Compra|Merma|Ajuste", message = "Motivo inválido")
    private String motivo;
    
    @Schema(description = "ID del usuario que realiza el movimiento", required = true, example = "123e4567-e89b-12d3-a456-426614174002")
    @JsonProperty("usuarioId")
    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID usuarioId;
    
    @Schema(description = "ID de referencia del movimiento", required = false, example = "123e4567-e89b-12d3-a456-426614174003")
    @JsonProperty("referenciaId")
    private String referenciaId;  // ← Cambiado de UUID a String
}