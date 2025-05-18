package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.Carrito;
import org.dgtic.maquetado.model.ItemCarrito;
import org.dgtic.maquetado.model.Producto;

import java.util.List;

public interface ItemCarritoService {

    ItemCarrito agregarProductoAlCarrito(Carrito carrito, Producto producto, int cantidad);

    List<ItemCarrito> obtenerItemsPorCarrito(Long idCarrito);

    void eliminarItem(Long idItemCarrito);

    void vaciarCarrito(Long idCarrito);


    List<ItemCarrito> findByCarritoId(Long carritoId);

}
