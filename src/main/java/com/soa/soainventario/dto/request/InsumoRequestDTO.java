package com.soa.soainventario.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsumoRequestDTO {
    
    @Schema(description = "Nombre del insumo", required = true, example = "Arroz")
    @JsonProperty("nombre")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @Schema(description = "Unidad de medida del insumo", required = true, example = "KILOS")
    @JsonProperty("unidadMedida")
    @NotBlank(message = "La unidad de medida es obligatoria")
    @Pattern(regexp = "KILOS|UNIDADES|LITROS", message = "Unidad de medida inválida")
    private String unidadMedida;
    
    @Schema(description = "Stock mínimo del insumo", required = true, example = "10.0")
    @JsonProperty("stockMinimo")
    @NotNull(message = "El stock mínimo es obligatorio")
    private double stockMinimo;
        
    @Schema(description = "Costo por unidad del insumo", required = true, example = "2.5")
    @JsonProperty("costoPorUnidad")
    @NotNull(message = "El costo por unidad es obligatorio")
    private double costoPorUnidad;
    
    @Schema(description = "Ubicación del insumo", required = false, example = "Pasillo 1")
    @JsonProperty("ubicacion")
    private String ubicacion;
}