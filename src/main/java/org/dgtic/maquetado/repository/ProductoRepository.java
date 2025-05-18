package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository  extends JpaRepository<Producto,Long> {

    Optional<Producto> findByNombreIgnoreCase(String nombre);

    Producto findByIdProducto(Long idProducto);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    Page<Producto> findByCategoriaIdCategoria(Long idCategoria, Pageable pageable);

}
