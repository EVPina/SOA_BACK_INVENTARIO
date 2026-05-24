package com.soa.soainventario.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class InsumoRequestDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "La unidad de medida es obligatoria")
    @Pattern(regexp = "KILOS|UNIDADES|LITROS", message = "Unidad de medida inválida")
    private String unidadMedida;
    
    @NotNull(message = "El stock mínimo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El stock mínimo debe ser mayor a 0")
    private BigDecimal stockMinimo;
    
    @NotNull(message = "El costo por unidad es obligatorio")
    @DecimalMin(value = "0.0", message = "El costo por unidad no puede ser negativo")
    private BigDecimal costoPorUnidad;
    
    private String ubicacion;
}