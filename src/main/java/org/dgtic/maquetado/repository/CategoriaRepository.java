package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

    Categoria findByTipo(String tipo);
}
