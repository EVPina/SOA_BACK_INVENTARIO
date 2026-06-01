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
@Table(name = "movimientos_inventario", schema = "public")
public class MovimientoInventario {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;
    
    @Column(nullable = false, length = 10)
    private String tipo;
    
    @Column(nullable = false)
    private double cantidad;
    
    @Column(name = "stock_resultante", nullable = false)
    private double stockResultante;
    
    @Column(nullable = false, length = 255)
    private String motivo;
    
    @Column(name = "usuario_id", nullable = false, columnDefinition = "uuid")
    private UUID usuarioId;
    
    @Column(name = "referencia_id", length = 100)  // ← Cambiado a String
    private String referenciaId;  // ← Cambiado de UUID a String
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}