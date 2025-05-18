package org.dgtic.maquetado.controller;

import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.HttpServletResponse;
import org.dgtic.maquetado.model.Cliente;
import org.dgtic.maquetado.repository.ClienteRepository;
import org.dgtic.maquetado.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    //@RequestMapping(value = "Login",method = RequestMethod.GET)
    public String Login(Model  modelo){
        modelo.addAttribute("login","Login");
        return "Login";
    }



    @PostMapping("/loginJwt")
    public String login(@RequestParam String correo,
                        @RequestParam String contraseña,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes) {

        var cliente = clienteRepository.findByCorreoElectronico(correo).orElse(null);

        if (cliente == null || !passwordEncoder.matches(contraseña, cliente.getContrasena())) {
            redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos");
            return "redirect:/auth/login";
        }

        String token = jwtTokenProvider.generarToken(
                cliente.getCorreoElectronico(),
                cliente.getRol().getNombre()
        );

        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        cookie.setSecure(false);
        cookie.setAttribute("SameSite", "Lax");
        response.addCookie(cookie);

        String rol = cliente.getRol().getNombre();

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return "redirect:/trabajador/inicio"; // página para admins
        } else if ("CLIENTE".equalsIgnoreCase(rol)) {
            return "redirect:/catalogoPaginacion"; // página para clientes
        } else {
            redirectAttributes.addFlashAttribute("error", "Rol no reconocido");
            return "redirect:/auth/login";
        }

    }





}
