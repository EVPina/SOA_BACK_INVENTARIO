package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ProduccionRequestDTO {

    @JsonProperty("productoId")
    @NotNull(message = "El ID del producto es obligatorio")
    @Schema(description = "ID del producto externo al que se le asignará la producción", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID productoId;  // ID externo del producto
    
    @JsonProperty("cantidad")
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Schema(description = "Cantidad de productos a producir", required = true, example = "10")
    private Integer cantidad;
    
    @JsonProperty("usuarioId")
    @NotNull(message = "El ID del usuario es obligatorio")
    @Schema(description = "ID del usuario que realiza la producción", required = true, example = "123e4567-e89b-12d3-a456-426614174002")
    private UUID usuarioId;
}