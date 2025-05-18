package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByClienteIdCliente(Long idCliente);
}
