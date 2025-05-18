package org.dgtic.maquetado.rutas;

import java.util.List;
import java.util.Set;

public class RutasIgnoradas {

   public static final Set<String> EXACTAS = Set.of(
            "/", "/login", "/auth/login", "/auth/loginJwt", "/auth/logout",
            "/registro", "/guardarUsuario"
    );

    public static final List<String> PREFIJOS = List.of(
            "/tema/", "/bootstrap/", "/imagenes/", "/iconos/", "/js/"
    );

}
