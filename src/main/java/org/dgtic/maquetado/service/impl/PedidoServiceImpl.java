package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.Cliente;
import org.dgtic.maquetado.model.EstadoPedido;
import org.dgtic.maquetado.model.Pedido;
import org.dgtic.maquetado.repository.ClienteRepository;
import org.dgtic.maquetado.repository.EstadoPedidoRepository;
import org.dgtic.maquetado.repository.PedidoRepository;
import org.dgtic.maquetado.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Override
    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido);
    }

    @Override
    public List<Pedido> buscarPedidoPorClienteId(Long idCliente) {
        return pedidoRepository.findByClienteIdCliente(idCliente);
    }

    @Override
    public List<Pedido> obtenerPedidosDelCliente(String correoCliente) {

        Cliente cliente = clienteRepository.findByCorreoElectronico(correoCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con correo: " + correoCliente));


        return pedidoRepository.findByClienteIdCliente(cliente.getIdCliente());
    }

    @Override
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public void actualizarEstado(Long idPedido, Long idEstadoPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow();
        EstadoPedido estado = estadoPedidoRepository.findById(idEstadoPedido).orElseThrow();
        pedido.setIdEstadoPedido(estado);
        pedidoRepository.save(pedido);
    }
}
