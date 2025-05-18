package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.Inventario;
import org.dgtic.maquetado.model.Producto;
import org.dgtic.maquetado.repository.InventarioRepository;
import org.dgtic.maquetado.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;


    @Override
    public Integer obtenerStockTotalPorProducto(Long idProducto) {
        Integer stock=inventarioRepository.stockTotalPorProducto(idProducto);
        return stock != null ? stock: 0 ;
    }
    @Override
    public void guardarInventario(Inventario inventario) {
        inventarioRepository.save(inventario);
    }

    @Override
    public void descontarStock(Producto producto, int cantidad) {
        List<Inventario> inventarios = inventarioRepository.findByProductoOrderByFechaRegistroAsc(producto);

        for (Inventario inv : inventarios) {
            int stockActual = inv.getStockActual();
            if (stockActual >= cantidad) {
                inv.setStockActual(stockActual - cantidad);
                inventarioRepository.save(inv);
                return;
            } else {
                cantidad -= stockActual;
                inv.setStockActual(0);
                inventarioRepository.save(inv);
            }
        }

        if (cantidad > 0) {
            throw new RuntimeException("No hay suficiente stock para el producto: " + producto.getNombre());
        }
    }

    @Override
    public List<Inventario> listarTodo() {
        return inventarioRepository.findAll();
    }



    @Override
    public List<Inventario> buscarPorSucursal(Long idSucursal) {
        return inventarioRepository.findBySucursal_IdSucursal(idSucursal);
    }

    @Override
    public List<Inventario> buscarPorNombreProducto(String nombre) {
        return inventarioRepository.findByProductoNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Optional<Inventario> obtenerPorProducto(Long idProducto) {
        return inventarioRepository.findByProductoIdProducto(idProducto);
    }
}
