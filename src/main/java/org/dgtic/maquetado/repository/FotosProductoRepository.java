package org.dgtic.maquetado.repository;

import org.dgtic.maquetado.model.FotosProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotosProductoRepository extends JpaRepository<FotosProducto,Long> {



    public List<FotosProducto>findByProducto_IdProducto(Long idFoto);




}
