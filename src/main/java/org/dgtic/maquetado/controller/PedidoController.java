package org.dgtic.maquetado.controller;

import org.dgtic.maquetado.model.Cliente;
import org.dgtic.maquetado.model.EstadoPedido;
import org.dgtic.maquetado.model.Pedido;
import org.dgtic.maquetado.repository.ClienteRepository;
import org.dgtic.maquetado.service.EstadoPedidoService;
import org.dgtic.maquetado.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private EstadoPedidoService estadoPedidoService;

    @GetMapping("/mis-pedidos")
    public String verPedidos(Model model, Principal principal) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosDelCliente(principal.getName());
        model.addAttribute("pedidos", pedidos);
        return "pedidos";
    }

    @GetMapping("/admin")
    public String verTodosLosPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        List<EstadoPedido> estados = estadoPedidoService.obtenerTodos();
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("estados", estados);
        return "PedidosAdmin";
    }

    @PostMapping("/admin/actualizar")
    public String actualizarEstado(@RequestParam Long idPedido,
                                   @RequestParam Long idEstadoPedido) {
        pedidoService.actualizarEstado(idPedido, idEstadoPedido);
        return "redirect:/pedidos/admin"; // recarga la tabla
    }
}
