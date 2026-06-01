package com.soa.soainventario.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsumoRequestDTO {
    
    @JsonProperty("nombre")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @JsonProperty("unidadMedida")
    @NotBlank(message = "La unidad de medida es obligatoria")
    @Pattern(regexp = "KILOS|UNIDADES|LITROS", message = "Unidad de medida inválida")
    private String unidadMedida;
    
    @JsonProperty("stockMinimo")
    @NotNull(message = "El stock mínimo es obligatorio")
    private double stockMinimo;
        
    @JsonProperty("costoPorUnidad")
    @NotNull(message = "El costo por unidad es obligatorio")
    private double costoPorUnidad;
    
    @JsonProperty("ubicacion")
    private String ubicacion;
}