// dto/response/StockResponseDTO.java
package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class StockResponseDTO {
    @Schema(description = "ID del insumo", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID insumoId;
    @Schema(description = "Nombre del insumo", example = "Lechuga")
    private String nombre;
    @Schema(description = "Stock actual del insumo", example = "10.0")  
    private double stockActual;
    @Schema(description = "Unidad de medida", example = "kg")
    private String unidadMedida;
    @Schema(description = "Indica si requiere alerta", example = "true")
    private Boolean requiereAlerta; // si está por debajo del mínimo
}