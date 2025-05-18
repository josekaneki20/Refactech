package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito,Long> {

    Optional<Carrito>findByClienteIdCliente(Long idCliente);


}
