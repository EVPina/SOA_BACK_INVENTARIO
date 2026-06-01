// dto/response/StockResponseDTO.java
package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class StockResponseDTO {
    private UUID insumoId;
    private String nombre;
    private double stockActual;
    private String unidadMedida;
    private Boolean requiereAlerta; // si está por debajo del mínimo
}