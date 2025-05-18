package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.Pagos;

import java.util.List;
import java.util.Optional;

public interface PagosService {

    void guardarPago(Pagos pago);
    List<Pagos> obtenerPagosPorPedido(Long idPedido);
}
