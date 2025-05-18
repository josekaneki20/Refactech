package org.dgtic.maquetado.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dgtic.maquetado.model.Marca;
import org.dgtic.maquetado.model.Producto;
import org.dgtic.maquetado.repository.RolesRepository;
import org.dgtic.maquetado.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class InicioController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private EstadoProductoService estadoProductoService;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private RolesRepository rolesRepository;

    //Traer un recurso mediante el application.properties
    @Value("mensaje.application")
    private String valor;


    //Le indicamos con este metodo la URL
    @GetMapping("/")
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    public String inicio(Model modelo)
    {
        modelo.addAttribute("mensaje","Bienvenido a RefacTech");
        return "inicio";

    }













}
