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
@Table(name = "insumos", schema = "public")
public class Insumo {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    
    @Column(nullable = false, length = 100, unique = true)
    private String nombre;
    
    @Column(name = "unidad_medida", nullable = false, length = 10)
    private String unidadMedida;
    
    @Column(name = "stock_actual", nullable = false)  // ← Sin precision/scale
    private double stockActual;
    
    @Column(name = "stock_minimo", nullable = false)  // ← Sin precision/scale
    private double stockMinimo;
    
    @Column(name = "costo_por_unidad", nullable = false)  // ← Sin precision/scale
    private double costoPorUnidad;
    
    @Column(length = 50)
    private String ubicacion;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        stockActual = 0.0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}