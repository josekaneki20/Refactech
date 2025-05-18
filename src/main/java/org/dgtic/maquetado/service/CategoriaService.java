package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoriaService {

    List<Categoria> listaCategorias();

    Categoria findByName(String nombre);



}
