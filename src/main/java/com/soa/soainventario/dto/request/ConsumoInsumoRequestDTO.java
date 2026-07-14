package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ConsumoInsumoRequestDTO {
    
    @Schema(description = "ID del producto externo al que se le asignará el insumo", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonProperty("productoId")
    @NotNull(message = "El ID del producto es obligatorio")
    private UUID productoId;  // ID externo del producto
    
    @Schema(description = "ID del insumo que se asignará al producto", required = true, example = "123e4567-e89b-12d3-a456-426614174001")   
    @JsonProperty("insumoId")
    @NotNull(message = "El ID del insumo es obligatorio")
    private UUID insumoId;
    
    @Schema(description = "Cantidad del insumo por porción", required = true, example = "2.5")
    @JsonProperty("cantidadPorPorcion")
    @NotNull(message = "La cantidad por porción es obligatoria")
    @Positive(message = "La cantidad por porción debe ser mayor a 0")
    private double cantidadPorPorcion;
}