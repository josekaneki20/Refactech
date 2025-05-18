package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.FotosProducto;
import org.dgtic.maquetado.model.Producto;
import org.dgtic.maquetado.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductoService {


    Optional<Producto>buscarPorNombre(String nombre);

    List<Producto> listaProductos();

    Producto obtenerProductoPorId(Long id);

    Producto guardarProducto(Producto producto);

    void eliminarProducto(Long id);

    void guardarFoto(FotosProducto fotosProducto);

    Page<Producto> obtenerProductosPaginados(int pagina, int tamano);

    Producto guardarProductoConFoto(Producto producto,FotosProducto fotosProducto);
    Page<Producto> obtenerProductosPorCategoria(Long categoriaId, int pagina, int tamano);







}
