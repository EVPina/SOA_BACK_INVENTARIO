package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class ResumenProductoDTO {
    @Schema(description = "ID del producto", example = "123e4567-e89b-12d3-a456-426614174006")
    private UUID productoId;
    @Schema(description = "Nombre del producto", example = "Hamburguesa")
    private String productoNombre;  // Este vendría del otro servicio
    @Schema(description = "Costo total de producción", example = "50.0")
    private double costoProduccionTotal;
    @Schema(description = "Cantidad de insumos utilizados", example = "5")
    private Integer cantidadInsumos;
    @Schema(description = "Precio de venta estimado", example = "10.0")
    private double precioVentaEstimado;  // Si se quiere sugerir precio
}