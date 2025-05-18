package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.FacturaPdf;
import org.dgtic.maquetado.model.Pedido;

import java.util.Optional;

public interface FacturaPdfService {

    byte[] generarYGuardarPdf(Pedido pedido) throws Exception;

    Optional<FacturaPdf> obtenerFacturaPorIdPedido(Long idPedido);
}
