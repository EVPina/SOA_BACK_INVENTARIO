package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class MovimientoRequestDTO {
    
    @JsonProperty("insumoId")
    @NotNull(message = "El ID del insumo es obligatorio")
    private UUID insumoId;
    
    @JsonProperty("tipo")
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "ENTRADA|SALIDA|AJUSTE", message = "Tipo de movimiento inválido")
    private String tipo;
    
    @JsonProperty("cantidad")
    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0")
    private double cantidad;
    
    @JsonProperty("motivo")
    @NotBlank(message = "El motivo es obligatorio")
    @Pattern(regexp = "Produccion|Compra|Merma|Ajuste", message = "Motivo inválido")
    private String motivo;
    
    @JsonProperty("usuarioId")
    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID usuarioId;
    
    @JsonProperty("referenciaId")
    private String referenciaId;  // ← Cambiado de UUID a String
}