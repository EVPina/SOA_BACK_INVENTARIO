// dto/request/DescuentoRequestDTO.java
package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class DescuentoRequestDTO {
    
    @JsonProperty("insumoId")
    @NotNull(message = "El ID del insumo es obligatorio")
    private UUID insumoId;
    
    @JsonProperty("cantidad")
    @NotNull(message = "La cantidad a descontar es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private double cantidad;

    @JsonProperty("usuarioId")
    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID usuarioId;
    
    @JsonProperty("ordenId")
    // Opcional: ID de la orden/pedido que se está marcando como LISTO
    private String ordenId;
}