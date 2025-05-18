package org.dgtic.maquetado.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserInfoService {
    UserDetails cargarPorCorreo(String correo);
}
