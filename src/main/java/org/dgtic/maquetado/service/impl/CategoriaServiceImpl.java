package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.Categoria;
import org.dgtic.maquetado.repository.CategoriaRepository;
import org.dgtic.maquetado.service.CategoriaService;
import org.dgtic.maquetado.validation.NoEspacioNoVacio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;


    @Override
    public List<Categoria> listaCategorias() {
        return categoriaRepository.findAll();
    }

    @NoEspacioNoVacio
    @Override
    public Categoria findByName(String nombre) {

        return categoriaRepository.findByTipo(nombre);
    }
}
