package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.Inventario;
import org.dgtic.maquetado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario,Long> {

    @Query("SELECT SUM(i.stockActual) FROM Inventario i WHERE i.producto.idProducto = :idProducto")
    Integer stockTotalPorProducto(@Param("idProducto") Long idProducto);
    List<Inventario> findByProductoOrderByFechaRegistroAsc(Producto producto);
    List<Inventario> findBySucursal_IdSucursal(Long idSucursal);
    List<Inventario> findByProductoNombreContainingIgnoreCase(String nombre);
    Optional<Inventario> findByProductoIdProducto(Long idProducto);
}
