package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito,Long> {

    List<ItemCarrito> findByCarritoIdCarrito(Long idCarrito);
    @Modifying
    @Transactional
    @Query("DELETE FROM ItemCarrito i WHERE i.carrito.idCarrito = :carritoId")
    void eliminarTodoPorCarrito(@Param("carritoId") Long carritoId);

}
