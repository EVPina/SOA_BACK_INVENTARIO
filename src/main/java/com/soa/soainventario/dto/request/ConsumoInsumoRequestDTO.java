package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ConsumoInsumoRequestDTO {
    
    @NotNull(message = "El ID del producto es obligatorio")
    private UUID productoId;  // ID externo del producto
    
    @NotNull(message = "El ID del insumo es obligatorio")
    private UUID insumoId;
    
    @NotNull(message = "La cantidad por porción es obligatoria")
    @Positive(message = "La cantidad por porción debe ser mayor a 0")
    private double cantidadPorPorcion;
}