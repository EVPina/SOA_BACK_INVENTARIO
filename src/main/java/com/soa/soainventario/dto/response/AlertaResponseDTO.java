package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder  // ← Esta anotación es necesaria
public class AlertaResponseDTO {
    private UUID id;
    private UUID insumoId;
    private String insumoNombre;
    private BigDecimal nivelActual;
    private BigDecimal nivelMinimo;
    private String unidadMedida;
    private String estado;
    private LocalDateTime creadaEn;
    private LocalDateTime resueltaEn;
}