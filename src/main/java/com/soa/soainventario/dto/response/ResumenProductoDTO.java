package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ResumenProductoDTO {
    private UUID productoId;
    private String productoNombre;  // Este vendría del otro servicio
    private BigDecimal costoProduccionTotal;
    private Integer cantidadInsumos;
    private BigDecimal precioVentaEstimado;  // Si se quiere sugerir precio
}