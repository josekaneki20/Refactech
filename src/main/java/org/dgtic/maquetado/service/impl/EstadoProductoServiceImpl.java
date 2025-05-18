package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.EstadoProducto;
import org.dgtic.maquetado.repository.EstadoProductoRepository;
import org.dgtic.maquetado.service.EstadoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoProductoServiceImpl implements EstadoProductoService {

    @Autowired
    EstadoProductoRepository estadoProductoRepository;

    @Override
    public List<EstadoProducto> listaEstadoproductos() {
        return estadoProductoRepository.findAll();
    }

    @Override
    public EstadoProducto findByEstado(String descripcion) {
        return estadoProductoRepository.findByDescripcion(descripcion);
    }


}
