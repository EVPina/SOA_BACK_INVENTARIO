package com.soa.soainventario.repository;

import com.soa.soainventario.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoInventario, UUID> {
    
    // Buscar movimientos por insumo
    List<MovimientoInventario> findByInsumoIdOrderByCreatedAtDesc(UUID insumoId);
    
    // Buscar movimientos por tipo
    List<MovimientoInventario> findByTipoAndCreatedAtBetween(String tipo, LocalDateTime start, LocalDateTime end);
    
    // Buscar movimientos por fecha
    List<MovimientoInventario> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    // Últimos movimientos
    @Query("SELECT m FROM MovimientoInventario m ORDER BY m.createdAt DESC")
    List<MovimientoInventario> findUltimosMovimientos();
    
    // Resumen de movimientos por día
    @Query("SELECT DATE(m.createdAt) as fecha, m.tipo, SUM(m.cantidad) as total " +
           "FROM MovimientoInventario m " +
           "WHERE m.createdAt >= :fechaInicio " +
           "GROUP BY DATE(m.createdAt), m.tipo " +
           "ORDER BY fecha DESC")
    List<Object[]> findResumenMovimientosPorDia(@Param("fechaInicio") LocalDateTime fechaInicio);
    
    // Total de entradas por insumo
    @Query("SELECT SUM(m.cantidad) FROM MovimientoInventario m " +
           "WHERE m.insumo.id = :insumoId AND m.tipo = 'ENTRADA'")
    double sumEntradasByInsumo(@Param("insumoId") UUID insumoId);
    
    // Total de salidas por insumo
    @Query("SELECT SUM(m.cantidad) FROM MovimientoInventario m " +
           "WHERE m.insumo.id = :insumoId AND m.tipo = 'SALIDA'")
    double sumSalidasByInsumo(@Param("insumoId") UUID insumoId);
}