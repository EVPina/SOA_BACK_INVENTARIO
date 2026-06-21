package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ProduccionRequestDTO {

    @JsonProperty("productoId")
    @NotNull(message = "El ID del producto es obligatorio")
    private UUID productoId;  // ID externo del producto
    
    @JsonProperty("cantidad")
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
    
    @JsonProperty("usuarioId")
    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID usuarioId;
}