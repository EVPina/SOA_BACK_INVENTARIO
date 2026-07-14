// dto/request/DescuentoRequestDTO.java
package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class DescuentoRequestDTO {
    
    @Schema(description = "ID del insumo del que se descontará stock", required = true, example = "123e4567-e89b-12d3-a456-426614174001")
    @JsonProperty("insumoId")
    @NotNull(message = "El ID del insumo es obligatorio")
    private UUID insumoId;
    
    @Schema(description = "Cantidad a descontar del stock del insumo", required = true, example = "2.5")
    @JsonProperty("cantidad")
    @NotNull(message = "La cantidad a descontar es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private double cantidad;

    @Schema(description = "ID del usuario que realiza el descuento", required = true, example = "123e4567-e89b-12d3-a456-426614174002")
    @JsonProperty("usuarioId")
    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID usuarioId;
    
    @Schema(description = "ID de la orden/pedido que se está marcando como LISTO", required = false, example = "123e4567-e89b-12d3-a456-426614174003")
    @JsonProperty("ordenId")
    // Opcional: ID de la orden/pedido que se está marcando como LISTO
    private String ordenId;
}