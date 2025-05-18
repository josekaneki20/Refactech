package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    Pedido guardar(Pedido pedido);
    Optional<Pedido> buscarPorId(Long idPedido);
    List<Pedido> buscarPedidoPorClienteId(Long idCliente);
    List<Pedido> obtenerPedidosDelCliente(String correoCliente);
    List<Pedido> obtenerTodosLosPedidos();
    void actualizarEstado(Long idPedido, Long idEstadoPedido);
}
