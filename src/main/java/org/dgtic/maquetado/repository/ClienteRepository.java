package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    Optional<Cliente> findByNumero(String numero);
    Optional<Cliente> findByCorreoElectronico(String correoElectronico);

}
