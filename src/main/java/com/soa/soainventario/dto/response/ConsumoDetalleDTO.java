package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class ConsumoDetalleDTO {
    @Schema(description = "ID del insumo", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID insumoId;

    @Schema(description = "Nombre del insumo", example = "Lechuga")
    private String insumoNombre;

    @Schema(description = "Cantidad consumida del insumo", example = "2.5")
    private double cantidadConsumida;

    @Schema(description = "Stock antes del consumo", example = "10.0")
    private double stockAntes;

    @Schema(description = "Stock después del consumo", example = "7.5")
    private double stockDespues;

    @Schema(description = "Costo unitario del insumo", example = "2.0")
    private double costoUnitario;

    @Schema(description = "Costo total del consumo", example = "5.0")
    private double costoTotal;

    @Schema(description = "Indica si hay suficiente stock", example = "true")
    private Boolean suficiente;
}