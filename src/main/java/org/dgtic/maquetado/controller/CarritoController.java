package org.dgtic.maquetado.controller;


import org.dgtic.maquetado.model.*;
import org.dgtic.maquetado.repository.ClienteRepository;
import org.dgtic.maquetado.repository.ProductoRepository;
import org.dgtic.maquetado.service.CarritoService;
import org.dgtic.maquetado.service.InventarioService;
import org.dgtic.maquetado.service.ItemCarritoService;
import org.dgtic.maquetado.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ItemCarritoService itemCarritoService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private InventarioService inventarioService;


    @GetMapping("/{idCliente}")
    public String verCarrito(@PathVariable Long idCliente, Model model) {
        Optional<Carrito> carritoOpt = carritoService.obtenerCarritoPorCliente(idCliente);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            List<ItemCarrito> items = itemCarritoService.obtenerItemsPorCarrito(carrito.getIdCarrito());

            // 🚀 Agrega esto para ver qué trae:
            System.out.println("Items en el carrito:");
            items.forEach(item -> {
                System.out.println("- Producto: " + item.getProducto().getNombre() + " | Cantidad: " + item.getCantidad());
            });

            model.addAttribute("items", items);
            model.addAttribute("total", carritoService.calcularTotalCarrito(carrito.getIdCarrito()));
        } else {
            model.addAttribute("items", List.of());
            model.addAttribute("total", 0.0);
        }
        model.addAttribute("idCliente", idCliente);
        return "carrito";
    }


    // Agregar producto al carrito
    @PostMapping("/agregar")
    public String agregarProductoAlCarrito(@RequestParam Long idCliente,
                                           @RequestParam Long idProducto,
                                           @RequestParam int cantidad) {

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Optional<Carrito> carritoOpt = carritoService.obtenerCarritoPorCliente(idCliente);
        Carrito carrito;
        if (carritoOpt.isPresent()) {
            carrito = carritoOpt.get();
        } else {
            carrito = carritoService.crearCarrito(cliente);
        }

        itemCarritoService.agregarProductoAlCarrito(carrito, producto, cantidad);

        return "redirect:/carrito/" + idCliente;
    }

    @GetMapping("/eliminar/{idCliente}/{idItemCarrito}")
    public String eliminarItemDelCarrito(@PathVariable Long idCliente, @PathVariable Long idItemCarrito) {
        itemCarritoService.eliminarItem(idItemCarrito);
        return "redirect:/carrito/" + idCliente;
    }

    @GetMapping("/vaciar/{idCliente}")
    public String vaciarCarrito(@PathVariable Long idCliente) {
        Optional<Carrito> carritoOpt = carritoService.obtenerCarritoPorCliente(idCliente);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            itemCarritoService.vaciarCarrito(carrito.getIdCarrito());
        }
        return "redirect:/carrito/" + idCliente;
    }

    @PostMapping("/finalizar/{idCliente}")
    public String finalizarCompra(@PathVariable Long idCliente, Model model) {
        Optional<Carrito> optionalCarrito = carritoService.obtenerCarritoPorCliente(idCliente);

        if (optionalCarrito.isEmpty()) {
            model.addAttribute("error", "Carrito no encontrado");
            return "redirect:/catalogoPaginacion";
        }

        Carrito carrito = optionalCarrito.get();
        List<ItemCarrito> items = itemCarritoService.findByCarritoId(carrito.getIdCarrito());

        if (items.isEmpty()) {
            model.addAttribute("error", "El carrito está vacío");
            return "redirect:/catalogoPaginacion";
        }

        // Calcular total
        double total = items.stream()
                .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                .sum();

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(carrito.getCliente());
        pedido.setFecha(LocalDateTime.now());
        pedido.setTotalCompra(total);
        pedido.setCarrito(carrito);

        EstadoPedido estado = new EstadoPedido();
        estado.setIdEstadoPedido(1L); // Pendiente
        pedido.setIdEstadoPedido(estado);

        pedidoService.guardar(pedido);


        for (ItemCarrito item : items) {
            inventarioService.descontarStock(item.getProducto(), item.getCantidad());
        }



        return "redirect:/pagos/formulario/" + pedido.getIdPedido();
    }



}
