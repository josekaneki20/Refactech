package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SucursalRepository extends JpaRepository<Sucursal,Long> {


}
