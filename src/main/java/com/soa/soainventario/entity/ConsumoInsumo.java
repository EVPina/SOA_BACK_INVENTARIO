package com.soa.soainventario.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "consumo_insumos")
public class ConsumoInsumo {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid")
    private UUID id;
    
    @Column(name = "producto_id", nullable = false, columnDefinition = "uuid")
    private UUID productoId;  // Solo el ID, sin relación JPA
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;
    
    @Column(name = "cantidad_por_porcion", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadPorPorcion;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}