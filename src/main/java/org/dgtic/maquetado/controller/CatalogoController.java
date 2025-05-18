package org.dgtic.maquetado.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.dgtic.maquetado.model.Cliente;
import org.dgtic.maquetado.model.Producto;

import org.dgtic.maquetado.repository.ClienteRepository;
import org.dgtic.maquetado.repository.ProductoRepository;
import org.dgtic.maquetado.security.jwt.JwtTokenProvider;
import org.dgtic.maquetado.service.CategoriaService;
import org.dgtic.maquetado.service.InventarioService;
import org.dgtic.maquetado.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class CatalogoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private InventarioService inventarioService;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;
    @GetMapping("/catalogoPaginacion")
    public String mostrarCatalogo(@RequestParam(defaultValue = "0") int pagina,
                                  @RequestParam(defaultValue = "10") int tamanoPagina,
                                  @RequestParam(required = false) Long idCliente,
                                  @RequestParam(required = false) Long categoriaId,
                                  Model model,
                                  HttpServletRequest request) {  // ← importante para acceder a cookies

        Page<Producto> productos;

        if (categoriaId != null) {
            productos = productoService.obtenerProductosPorCategoria(categoriaId, pagina, tamanoPagina);
            model.addAttribute("categoriaId", categoriaId);
        } else {
            productos = productoService.obtenerProductosPaginados(pagina, tamanoPagina);
        }

        Map<Long, Integer> stockPorProducto = new HashMap<>();
        for (Producto p : productos.getContent()) {
            stockPorProducto.put(p.getIdProducto(), inventarioService.obtenerStockTotalPorProducto(p.getIdProducto()));
        }

        model.addAttribute("productos", productos.getContent());
        model.addAttribute("totalPages", productos.getTotalPages());
        model.addAttribute("currentPage", pagina);
        model.addAttribute("totalItems", productos.getTotalElements());
        model.addAttribute("categorias", categoriaService.listaCategorias());
        model.addAttribute("stockPorProducto", stockPorProducto);

        // Obtener nombre del cliente desde JWT en cookie
        String token = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> "JWT".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (token != null && tokenProvider.esTokenValido(token)) {
            String correo = tokenProvider.obtenerCorreoDesdeToken(token);
            Cliente cliente = clienteRepository.findByCorreoElectronico(correo).orElse(null);
            if (cliente != null) {
                model.addAttribute("nombreUsuario", cliente.getNombre());
                idCliente = cliente.getIdCliente(); // ← también actualiza el ID del cliente logueado
            }
        }

        if (idCliente == null) {
            idCliente = 5L; // Cliente por defecto si no se detecta (opcional)
        }

        model.addAttribute("idCliente", idCliente);

        return "CatalogoPaginacion";
    }



    @GetMapping("/catalogo/buscar")
    public String buscarProductos(@RequestParam("termino") String termino, Model model) {
        // Buscar productos que coincidan con el término de búsqueda
        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCase(termino);
        model.addAttribute("productos", productos);
        return "Catalogo";  // Regresar a la vista de catálogo con los productos filtrados
    }


}
