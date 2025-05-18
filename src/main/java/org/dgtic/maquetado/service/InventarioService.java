package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.Inventario;
import org.dgtic.maquetado.model.Producto;

import java.util.List;
import java.util.Optional;

public interface InventarioService {
    Integer obtenerStockTotalPorProducto(Long idProducto);

    void guardarInventario(Inventario inventario);
    void descontarStock(Producto producto, int cantidad);
    List<Inventario> listarTodo();

    List<Inventario> buscarPorSucursal(Long idSucursal);

    List<Inventario> buscarPorNombreProducto(String nombre);
    Optional<Inventario> obtenerPorProducto(Long idProducto);
}
