package org.dgtic.maquetado.service;



import org.dgtic.maquetado.model.Carrito;
import org.dgtic.maquetado.model.Cliente;

import java.util.Optional;

public interface CarritoService {

    Carrito crearCarrito(Cliente cliente);

    Optional<Carrito> obtenerCarritoPorCliente(Long idCliente);

    Carrito guardarCarrito(Carrito carrito);

    void eliminarCarrito(Long idCarrito);

    double calcularTotalCarrito(Long idCarrito);

}
