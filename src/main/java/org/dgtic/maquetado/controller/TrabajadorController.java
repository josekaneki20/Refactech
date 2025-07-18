package org.dgtic.maquetado.controller;

import org.dgtic.maquetado.model.Cliente;
import org.dgtic.maquetado.model.Roles;
import org.dgtic.maquetado.repository.RolesRepository;
import org.dgtic.maquetado.service.ClienteService;
import org.dgtic.maquetado.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/trabajador")
public class TrabajadorController {


    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RolesService rolesService;



    @GetMapping("/Registro")
    //@RequestMapping(value = "Registro",method = RequestMethod.GET)
    public String paginaRegistrar(Model modelo){
        modelo.addAttribute("registro","Registro");
        return "RegistroUsuarios";

    }


    // Procesar formulario
    @PostMapping("/guardarTrabajador")
    public String procesarFormulario(@RequestParam String nombre,
                                     @RequestParam String apellidoPaterno,
                                     @RequestParam String apellidoMaterno,
                                     @RequestParam Integer edad,
                                     @RequestParam char sexo,
                                     @RequestParam String numero,
                                     @RequestParam String correo,
                                     @RequestParam String contrasena,
                                     @RequestParam String direccion,
                                     Model model) {

        List<String> errores = new ArrayList<>();

        // Validaciones básicas
        if (nombre == null || nombre.trim().isEmpty()) {
            errores.add("El nombre no puede estar vacío.");
        }
        if (apellidoPaterno == null || apellidoPaterno.trim().isEmpty()) {
            errores.add("El apellido paterno es obligatorio.");
        }
        if (edad == null || edad < 18 || edad > 70) {
            errores.add("La edad debe estar entre 18 y 70 años.");
        }
        if (!numero.matches("^[0-9]{10}$")) {
            errores.add("El número de teléfono debe tener 10 dígitos.");
        }
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errores.add("Debe ingresar un correo válido.");
        }
        if (contrasena.length() < 6) {
            errores.add("La contraseña debe tener al menos 6 caracteres.");
        }
        if (clienteService.existeNumero(numero)) {
            errores.add("Este número de teléfono ya está registrado.");
        }
        if (clienteService.existeCorreo(correo)) {
            errores.add("El correo ya está registrado.");
        }

        if (errores.isEmpty()) {
            String hashedPassword = passwordEncoder.encode(contrasena);


            Cliente cliente = new Cliente(nombre, apellidoPaterno, apellidoMaterno, edad,
                    sexo, numero, correo, hashedPassword, direccion);
            Roles rolCliente = rolesRepository.findById(1L).orElseThrow();
            cliente.setRol(rolCliente);


            clienteService.guardarCliente(cliente);
            model.addAttribute("mensaje", "Formulario enviado con éxito");
        } else {
            model.addAttribute("errores", errores);
        }


        return "RegistroUsuarios";
    }


    @GetMapping("/inicio")
    public String paginaInicio(Model model, Principal principal) {
        model.addAttribute("nombreUsuario", principal.getName());
        return "inicio-trabajador"; // vista HTML en templates/paginas/
    }




}
