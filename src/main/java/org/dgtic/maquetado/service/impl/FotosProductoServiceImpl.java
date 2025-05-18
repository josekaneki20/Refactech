package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.FotosProducto;
import org.dgtic.maquetado.repository.FotosProductoRepository;
import org.dgtic.maquetado.service.FotosProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FotosProductoServiceImpl implements FotosProductoService {


    @Autowired
    private FotosProductoRepository fotosProductoRepository;


    @Override
    public List<FotosProducto> obtenerFotosPorProducto(Long idProducto) {
        return fotosProductoRepository.findByProducto_IdProducto(idProducto);
    }

    @Override
    public FotosProducto guardarFoto(FotosProducto foto) {
        return fotosProductoRepository.save(foto);
    }

    @Override
    public void eliminarFoto(long id) {
        fotosProductoRepository.deleteById(id);
    }

}
