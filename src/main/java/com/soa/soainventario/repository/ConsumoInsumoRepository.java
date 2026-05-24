package com.soa.soainventario.repository;

import com.soa.soainventario.entity.ConsumoInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConsumoInsumoRepository extends JpaRepository<ConsumoInsumo, UUID> {
    
    // Buscar consumos por producto (usando el ID externo)
    List<ConsumoInsumo> findByProductoId(UUID productoId);
    
    // Buscar consumos con insumo por producto
    @Query("SELECT c FROM ConsumoInsumo c JOIN FETCH c.insumo WHERE c.productoId = :productoId")
    List<ConsumoInsumo> findConsumosWithInsumoByProductoId(@Param("productoId") UUID productoId);
    
    // Buscar consumo específico
    ConsumoInsumo findByProductoIdAndInsumoId(UUID productoId, UUID insumoId);
    
    // Verificar si ya existe la relación
    boolean existsByProductoIdAndInsumoId(UUID productoId, UUID insumoId);
    
    // Eliminar todos los consumos de un producto
    @Modifying
    @Transactional
    @Query("DELETE FROM ConsumoInsumo c WHERE c.productoId = :productoId")
    void deleteByProductoId(@Param("productoId") UUID productoId);
    
    // Eliminar consumo específico
    @Modifying
    @Transactional
    void deleteByProductoIdAndInsumoId(UUID productoId, UUID insumoId);
    
    // Calcular costo total de producción (necesita costo por unidad del insumo)
    @Query("SELECT SUM(c.cantidadPorPorcion * i.costoPorUnidad) FROM ConsumoInsumo c " +
           "JOIN c.insumo i WHERE c.productoId = :productoId")
    BigDecimal calcularCostoProduccion(@Param("productoId") UUID productoId);
    
    // Verificar stock para producción (usando productoId externo)
    @Query("SELECT c.insumo.id, c.insumo.nombre, c.insumo.stockActual, " +
           "c.cantidadPorPorcion, (c.cantidadPorPorcion * :cantidadProductos) as cantidadNecesaria " +
           "FROM ConsumoInsumo c WHERE c.productoId = :productoId")
    List<Object[]> verificarStockParaProduccion(@Param("productoId") UUID productoId, 
                                                  @Param("cantidadProductos") BigDecimal cantidadProductos);
    
    // Obtener resumen de insumos por producto
    @Query("SELECT c.insumo.id, c.insumo.nombre, c.insumo.unidadMedida, " +
           "c.cantidadPorPorcion, c.insumo.costoPorUnidad, " +
           "(c.cantidadPorPorcion * c.insumo.costoPorUnidad) as costoPorUnidadProducto " +
           "FROM ConsumoInsumo c WHERE c.productoId = :productoId")
    List<Object[]> getResumenInsumosPorProducto(@Param("productoId") UUID productoId);
    
    // Obtener productos que usan un insumo específico
    @Query("SELECT DISTINCT c.productoId FROM ConsumoInsumo c WHERE c.insumo.id = :insumoId")
    List<UUID> findProductosByInsumoId(@Param("insumoId") UUID insumoId);
}