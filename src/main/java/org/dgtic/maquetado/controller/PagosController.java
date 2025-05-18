package org.dgtic.maquetado.controller;

import com.lowagie.text.pdf.AcroFields;
import jakarta.validation.Valid;
import org.dgtic.maquetado.model.*;
import org.dgtic.maquetado.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pagos")
public class PagosController {

    @Autowired
    private PagosService pagosService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ItemCarritoService itemCarritoService;

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/formulario/{idPedido}")
    public String mostrarFormularioPago(@PathVariable Long idPedido, Model model) {
        Optional<Pedido> optionalPedido = pedidoService.buscarPorId(idPedido);

        if (optionalPedido.isEmpty()) {
            model.addAttribute("error", "Pedido no encontrado");
            return "redirect:/catalogoPaginacion";
        }

        Pedido pedido = optionalPedido.get();
        model.addAttribute("pedido", pedido);
        model.addAttribute("pago", new Pagos());
        model.addAttribute("idPedido", idPedido);
        model.addAttribute("total", pedido.getTotalCompra());

        return "Pagos"; // vista del formulario
    }

    @PostMapping("/guardar")
    public String procesarPago(@Valid @ModelAttribute("pago") Pagos pago,
                               BindingResult errores,
                               @RequestParam Long idPedido,
                               Model model) {

        if (errores.hasErrors()) {
            Optional<Pedido> pedidoError = pedidoService.buscarPorId(idPedido);
            pedidoError.ifPresent(p -> {
                model.addAttribute("pedido", p);
                model.addAttribute("total", p.getTotalCompra());
                model.addAttribute("idPedido", idPedido);
            });
            return "Pagos";
        }

        Optional<Pedido> optionalPedido = pedidoService.buscarPorId(idPedido);
        if (optionalPedido.isEmpty()) {
            model.addAttribute("error", "Pedido no encontrado");
            return "redirect:/catalogoPaginacion";
        }

        Pedido pedido = optionalPedido.get();
        pago.setPedido(pedido);
        pago.setMonto(pedido.getTotalCompra());

        pagosService.guardarPago(pago);

        model.addAttribute("mensajeExito", "Pago registrado correctamente.");
        return "redirect:/pagos/factura/vista?idPedido=" + idPedido; // flujo original
    }

    @GetMapping("/factura/vista")
    public String mostrarFactura(@RequestParam Long idPedido, Model model) {
        Optional<Pedido> optionalPedido = pedidoService.buscarPorId(idPedido);
        if (optionalPedido.isEmpty()) {
            model.addAttribute("error", "Pedido no encontrado.");
            return "redirect:/catalogoPaginacion";
        }

        Pedido pedido = optionalPedido.get();
        Carrito carrito = pedido.getCarrito();
        List<ItemCarrito> productos = itemCarritoService.findByCarritoId(carrito.getIdCarrito());

        model.addAttribute("pedido", pedido);
        model.addAttribute("cliente", pedido.getCliente());
        model.addAttribute("productos", productos);
        model.addAttribute("total", pedido.getTotalCompra());

        return "factura";


    }

    @PostMapping("/vaciarYvolver")
    public String vaciarYVolver(@RequestParam Long idCliente) {
        Optional<Carrito> carritoOpt = carritoService.obtenerCarritoPorCliente(idCliente);
        carritoOpt.ifPresent(c -> itemCarritoService.vaciarCarrito(c.getIdCarrito()));

        return "redirect:/catalogoPaginacion";
    }
}
