package com.soa.soainventario.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder  // ← Esta anotación es necesaria para usar builder()
public class MovimientoResponseDTO {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("insumoId")
    private UUID insumoId;
    @JsonProperty("insumoNombre")
    private String insumoNombre;
    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("cantidad")
    private double cantidad;
    @JsonProperty("stockResultante")
    private double stockResultante;
    @JsonProperty("motivo")
    private String motivo;
    @JsonProperty("usuarioId")
    private UUID usuarioId;
    @JsonProperty("referenciaId")
    private String referenciaId;
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}