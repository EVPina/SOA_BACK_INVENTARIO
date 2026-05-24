package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class InsumoResponseDTO {
    private UUID id;
    private String nombre;
    private String unidadMedida;
    private BigDecimal stockActual;
    private BigDecimal stockMinimo;
    private BigDecimal costoPorUnidad;
    private String ubicacion;
    private String estadoStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}