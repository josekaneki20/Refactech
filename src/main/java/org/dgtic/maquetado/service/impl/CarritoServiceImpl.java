package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.Carrito;
import org.dgtic.maquetado.model.Cliente;
import org.dgtic.maquetado.repository.CarritoRepository;
import org.dgtic.maquetado.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;


    @Override
    public Carrito crearCarrito(Cliente cliente) {
        Carrito carrito = new Carrito();
        carrito.setCliente(cliente);
        carrito.setFechaCreacion(new java.util.Date());
        carrito.setTotalCarrito(0.0);
        return carritoRepository.save(carrito);
    }

    @Override
    public Optional<Carrito> obtenerCarritoPorCliente(Long idCliente) {

        return carritoRepository.findByClienteIdCliente(idCliente);
    }

    @Override
    public Carrito guardarCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    @Override
    public void eliminarCarrito(Long idCarrito) {
        carritoRepository.deleteById(idCarrito);

    }

    @Override
    public double calcularTotalCarrito(Long idCarrito) {
        Optional<Carrito> carritoOpt = carritoRepository.findById(idCarrito);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            return carrito.getItems()
                    .stream()
                    .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                    .sum();
        }
        return 0.0;
    }
}
