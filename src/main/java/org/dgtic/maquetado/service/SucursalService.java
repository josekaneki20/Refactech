package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.Sucursal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SucursalService {

    List<Sucursal> listarSucursales();

    // Buscar sucursal por ID
    Optional<Sucursal> buscarPorId(Long id);

    Sucursal guardarSucursal(Sucursal sucursal);

    // Eliminar sucursal por ID
    void eliminarSucursal(Long id);
}
