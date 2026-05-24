package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MovimientoRequestDTO {
    
    @NotNull(message = "El ID del insumo es obligatorio")
    private UUID insumoId;
    
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "ENTRADA|SALIDA|AJUSTE", message = "Tipo de movimiento inválido")
    private String tipo;
    
    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0")
    private BigDecimal cantidad;
    
    @NotBlank(message = "El motivo es obligatorio")
    @Pattern(regexp = "Produccion|Compra|Merma|Ajuste", message = "Motivo inválido")
    private String motivo;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID usuarioId;
    
    private UUID referenciaId;
}