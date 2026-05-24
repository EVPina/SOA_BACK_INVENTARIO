package com.soa.soainventario.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(schema="public",name = "alertas_stock")
public class AlertaStock {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid")
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;
    
    @Column(name = "nivel_actual", nullable = false, precision = 10, scale = 2)
    private BigDecimal nivelActual;
    
    @Column(name = "nivel_minimo", nullable = false, precision = 10, scale = 2)
    private BigDecimal nivelMinimo;
    
    @Column(nullable = false, length = 10)
    private String estado;
    
    @Column(name = "creada_en", nullable = false)
    private LocalDateTime creadaEn;
    
    @Column(name = "resuelta_en")
    private LocalDateTime resueltaEn;
    
    @PrePersist
    protected void onCreate() {
        creadaEn = LocalDateTime.now();
        if (estado == null) {
            estado = "ACTIVA";
        }
    }
}