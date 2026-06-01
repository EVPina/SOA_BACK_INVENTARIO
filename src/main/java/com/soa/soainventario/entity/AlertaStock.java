package com.soa.soainventario.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alertas_stock", schema = "public")
public class AlertaStock {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;
    
    @Column(name = "nivel_actual", nullable = false)  // ← Sin precision/scale
    private double nivelActual;
    
    @Column(name = "nivel_minimo", nullable = false)  // ← Sin precision/scale
    private double nivelMinimo;
    
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