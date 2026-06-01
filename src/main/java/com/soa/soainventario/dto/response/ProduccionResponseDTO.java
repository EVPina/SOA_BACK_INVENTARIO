package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ProduccionResponseDTO {
    private UUID productoId;  // ID externo del producto
    private double cantidadProducida;
    private double costoTotalProduccion;
    private List<ConsumoDetalleDTO> insumosConsumidos;
    private Boolean exito;
    private String mensaje;
}