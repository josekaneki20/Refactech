package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.FacturaPdf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacturaPdfRepository extends JpaRepository<FacturaPdf,Long> {


    Optional<FacturaPdf> findByPedidoIdPedido(Long idPedido);


}
