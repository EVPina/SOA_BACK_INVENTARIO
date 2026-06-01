package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ConsumoDetalleDTO {
    private UUID insumoId;
    private String insumoNombre;
    private double cantidadConsumida;
    private double stockAntes;
    private double stockDespues;
    private double costoUnitario;
    private double costoTotal;
    private Boolean suficiente;
}