package com.soa.soainventario.repository;

import com.soa.soainventario.entity.AlertaStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AlertaRepository extends JpaRepository<AlertaStock, UUID> {
    
    // Buscar alertas activas por insumo
    List<AlertaStock> findByInsumoIdAndEstado(UUID insumoId, String estado);
    
    // Buscar todas las alertas activas
    List<AlertaStock> findByEstado(String estado);
    
    // Buscar alertas por rango de fechas
    List<AlertaStock> findByCreadaEnBetween(LocalDateTime start, LocalDateTime end);
    
    // Verificar si existe alerta activa para un insumo
    boolean existsByInsumoIdAndEstado(UUID insumoId, String estado);
    
    // Actualizar alertas resueltas
    @Modifying
    @Transactional
    @Query("UPDATE AlertaStock a SET a.estado = 'RESUELTA', a.resueltaEn = :resueltaEn WHERE a.insumo.id = :insumoId AND a.estado = 'ACTIVA'")
    int resolverAlertasPorInsumo(@Param("insumoId") UUID insumoId, @Param("resueltaEn") LocalDateTime resueltaEn);
    
    // Contar alertas activas
    @Query("SELECT COUNT(a) FROM AlertaStock a WHERE a.estado = 'ACTIVA'")
    long countAlertasActivas();
    
    // Buscar alertas activas con insumo
    @Query("SELECT a FROM AlertaStock a JOIN FETCH a.insumo WHERE a.estado = 'ACTIVA'")
    List<AlertaStock> findAlertasActivasWithInsumo();
}