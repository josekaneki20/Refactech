package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.FotosProducto;

import java.util.List;

public interface FotosProductoService {

    List<FotosProducto> obtenerFotosPorProducto(Long idProducto);

    FotosProducto guardarFoto(FotosProducto foto);

    void eliminarFoto(long id);
}
