package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.Producto;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CatalogoRepository extends PagingAndSortingRepository<Producto,Long>{


}
