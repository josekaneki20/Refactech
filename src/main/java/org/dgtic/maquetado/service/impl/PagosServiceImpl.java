package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.Pagos;
import org.dgtic.maquetado.repository.PagosRepository;
import org.dgtic.maquetado.service.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PagosServiceImpl implements PagosService {

    @Autowired
    private PagosRepository pagosRepository;

    @Override
    public void guardarPago(Pagos pago) {
        pagosRepository.save(pago);
    }

    @Override
    public List<Pagos> obtenerPagosPorPedido(Long idPedido) {
        return pagosRepository.findByPedidoIdPedido(idPedido);
    }
}
