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
    private BigDecimal cantidadConsumida;
    private BigDecimal stockAntes;
    private BigDecimal stockDespues;
    private BigDecimal costoUnitario;
    private BigDecimal costoTotal;
    private Boolean suficiente;
}