package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class ProduccionResponseDTO {
    @Schema(description = "ID de la producción", example = "123e4567-e89b-12d3-a456-426614174005")
    private UUID productoId;  // ID externo del producto
    @Schema(description = "Cantidad de productos producidos", example = "10.0")
    private double cantidadProducida;
    @Schema(description = "Costo total de la producción", example = "100.0")
    private double costoTotalProduccion;
    @Schema(description = "Lista de insumos consumidos en la producción", example = "[]")
    private List<ConsumoDetalleDTO> insumosConsumidos;
    @Schema(description = "Indica si la producción fue exitosa", example = "true")
    private Boolean exito;
    @Schema(description = "Mensaje de resultado de la producción", example = "Producción realizada con éxito")
    private String mensaje;
}