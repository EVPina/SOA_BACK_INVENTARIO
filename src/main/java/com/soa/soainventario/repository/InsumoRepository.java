package com.soa.soainventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soa.soainventario.entity.Insumo;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface InsumoRepository extends JpaRepository<Insumo, UUID> {
    
    List<Insumo> findByStockActualLessThan(BigDecimal stockMinimo);
    
    List<Insumo> findByStockActualLessThanEqualOrderByStockActualAsc(BigDecimal stockMinimo);
    
    @Query("SELECT i FROM Insumo i WHERE i.stockActual < i.stockMinimo")
    List<Insumo> findInsumosConStockBajo();
    
    boolean existsByNombreIgnoreCase(String nombre);
}