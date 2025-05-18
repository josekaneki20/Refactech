package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.EstadoPedido;
import org.dgtic.maquetado.repository.EstadoPedidoRepository;
import org.dgtic.maquetado.service.EstadoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoServiceImpl implements EstadoPedidoService {

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Override
    public List<EstadoPedido> obtenerTodos() {
        return estadoPedidoRepository.findAll();
    }
}
