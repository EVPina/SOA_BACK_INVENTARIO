package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder  // ← Esta anotación es necesaria para usar builder()
public class MovimientoResponseDTO {
    private UUID id;
    private UUID insumoId;
    private String insumoNombre;
    private String tipo;
    private BigDecimal cantidad;
    private BigDecimal stockResultante;
    private String motivo;
    private UUID usuarioId;
    private UUID referenciaId;
    private LocalDateTime createdAt;
}