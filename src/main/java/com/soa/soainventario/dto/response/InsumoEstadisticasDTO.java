package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class InsumoEstadisticasDTO {
    private UUID insumoId;
    private String insumoNombre;  // Opcional: para mostrar el nombre
    private double totalEntradas;
    private double totalSalidas;
    private double saldoActual;
    private Integer totalMovimientos;  // Opcional: contador de movimientos
}