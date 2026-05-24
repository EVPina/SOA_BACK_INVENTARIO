package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class ProduccionRequestDTO {
    
    @NotNull(message = "El ID del producto es obligatorio")
    private UUID productoId;  // ID externo del producto
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID usuarioId;
}