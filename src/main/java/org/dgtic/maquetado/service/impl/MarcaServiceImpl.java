package org.dgtic.maquetado.service.impl;


import org.dgtic.maquetado.model.Marca;
import org.dgtic.maquetado.repository.MarcaRepository;
import org.dgtic.maquetado.service.MarcaService;
import org.dgtic.maquetado.validation.NoEspacioNoVacio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Override
    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }
}
