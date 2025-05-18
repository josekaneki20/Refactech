package org.dgtic.maquetado.service.impl;

import jakarta.transaction.Transactional;
import org.dgtic.maquetado.model.FotosProducto;
import org.dgtic.maquetado.model.Producto;

import org.dgtic.maquetado.repository.CatalogoRepository;
import org.dgtic.maquetado.repository.FotosProductoRepository;
import org.dgtic.maquetado.repository.ProductoRepository;
import org.dgtic.maquetado.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FotosProductoRepository fotosProductoRepository;

    @Autowired
    private CatalogoRepository catalogoRepository;

    @Override
    public Page<Producto> obtenerProductosPaginados(int pagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(pagina, tamano);
        return catalogoRepository.findAll(pageRequest);
    }

    @Override
    public List<Producto> listaProductos() {

        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {

        return productoRepository.findByIdProducto(id);
    }

    @Override
    @Transactional
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public void guardarFoto(FotosProducto fotosProducto) {

    }

    @Override
    public Producto guardarProductoConFoto(Producto producto, FotosProducto fotosProducto) {
        Producto productoGuardado= productoRepository.save(producto);

        fotosProducto.setProducto(productoGuardado);
        fotosProductoRepository.save(fotosProducto);
        return productoGuardado;
    }

    @Override
    public Page<Producto> obtenerProductosPorCategoria(Long categoriaId, int pagina, int tamano) {
        return  productoRepository.findByCategoriaIdCategoria(categoriaId, PageRequest.of(pagina, tamano));
    }

    @Override
    public Optional<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreIgnoreCase(nombre);
    }
}
