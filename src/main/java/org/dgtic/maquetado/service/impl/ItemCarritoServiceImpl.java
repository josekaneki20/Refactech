package org.dgtic.maquetado.service.impl;

import jakarta.transaction.Transactional;
import org.dgtic.maquetado.model.Carrito;
import org.dgtic.maquetado.model.Inventario;
import org.dgtic.maquetado.model.ItemCarrito;
import org.dgtic.maquetado.model.Producto;
import org.dgtic.maquetado.repository.InventarioRepository;
import org.dgtic.maquetado.repository.ItemCarritoRepository;
import org.dgtic.maquetado.service.InventarioService;
import org.dgtic.maquetado.service.ItemCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemCarritoServiceImpl implements ItemCarritoService {
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Transactional
    @Override
    public ItemCarrito agregarProductoAlCarrito(Carrito carrito, Producto producto, int cantidad) {
        ItemCarrito item = new ItemCarrito();
        item.setCarrito(carrito);
        item.setProducto(producto);
        item.setCantidad(cantidad);
        itemCarritoRepository.save(item);
        itemCarritoRepository.flush();
        return item;
    }

    @Override
    public List<ItemCarrito> obtenerItemsPorCarrito(Long idCarrito) {
        return itemCarritoRepository.findByCarritoIdCarrito(idCarrito);
    }

    @Override
    public void eliminarItem(Long idItemCarrito) {
        itemCarritoRepository.deleteById(idItemCarrito);
    }

    @Override
    public void vaciarCarrito(Long idCarrito) {
        itemCarritoRepository.eliminarTodoPorCarrito(idCarrito);
    }


    @Override
    public List<ItemCarrito> findByCarritoId(Long carritoId) {
        return itemCarritoRepository.findByCarritoIdCarrito(carritoId);
    }
}
