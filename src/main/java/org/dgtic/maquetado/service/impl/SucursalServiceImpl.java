package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.Sucursal;
import org.dgtic.maquetado.repository.SucursalRepository;
import org.dgtic.maquetado.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SucursalServiceImpl implements SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public List<Sucursal> listarSucursales() {
        return sucursalRepository.findAll();
    }

    @Override
    public Optional<Sucursal> buscarPorId(Long id) {
        return sucursalRepository.findById(id);
    }

    @Override
    public Sucursal guardarSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    @Override
    public void eliminarSucursal(Long id) {
        sucursalRepository.deleteById(id);
    }
}
