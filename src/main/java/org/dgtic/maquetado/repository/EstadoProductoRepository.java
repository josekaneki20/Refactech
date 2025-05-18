package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoProductoRepository  extends JpaRepository<EstadoProducto,Long> {


    EstadoProducto findByDescripcion(String descripcion);

}
