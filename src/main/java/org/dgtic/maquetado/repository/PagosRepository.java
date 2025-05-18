package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagosRepository extends JpaRepository<Pagos,Long> {
    List<Pagos> findByPedidoIdPedido(Long idPedido);
}
