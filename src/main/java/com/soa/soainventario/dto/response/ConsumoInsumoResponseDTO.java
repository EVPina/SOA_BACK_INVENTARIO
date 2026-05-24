package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ConsumoInsumoResponseDTO {
    private UUID id;
    private UUID productoId;  // ID externo del producto
    private UUID insumoId;
    private String insumoNombre;
    private String unidadMedida;
    private BigDecimal cantidadPorPorcion;
    private BigDecimal costoUnitario;
    private BigDecimal costoTotal;
    private LocalDateTime createdAt;
}