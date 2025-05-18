package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.EstadoProducto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EstadoProductoService {



    List<EstadoProducto> listaEstadoproductos();
    EstadoProducto findByEstado(String descripcion);





}
