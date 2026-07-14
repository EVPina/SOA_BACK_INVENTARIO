package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class InsumoEstadisticasDTO {
    @Schema(description = "ID del insumo", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID insumoId;
    @Schema(description = "Nombre del insumo", example = "Lechuga")
    private String insumoNombre;  // Opcional: para mostrar el nombre
    @Schema(description = "Total de entradas del insumo", example = "10.0")
    private double totalEntradas;
    @Schema(description = "Total de salidas del insumo", example = "5.0")
    private double totalSalidas;
    @Schema(description = "Saldo actual del insumo", example = "5.0")
    private double saldoActual;
    @Schema(description = "Total de movimientos del insumo", example = "15")
    private Integer totalMovimientos;  // Opcional: contador de movimientos
}