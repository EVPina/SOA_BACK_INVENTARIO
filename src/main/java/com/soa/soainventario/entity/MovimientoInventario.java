package com.soa.soainventario.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(schema="public",name = "movimientos_inventario")
public class MovimientoInventario {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid")
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;
    
    @Column(nullable = false, length = 10)
    private String tipo;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;
    
    @Column(name = "stock_resultante", nullable = false, precision = 10, scale = 2)
    private BigDecimal stockResultante;
    
    @Column(nullable = false, length = 255)
    private String motivo;
    
    @Column(name = "usuario_id", nullable = false, columnDefinition = "uuid")
    private UUID usuarioId;
    
    @Column(name = "referencia_id", columnDefinition = "uuid")
    private UUID referenciaId;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}