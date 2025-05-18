package org.dgtic.maquetado.security.service;

import org.dgtic.maquetado.model.Cliente;
import org.dgtic.maquetado.repository.ClienteRepository;
import org.dgtic.maquetado.security.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails cargarPorCorreo(String correo) {
        Cliente cliente = clienteRepository.findByCorreoElectronico(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UserDetailsImpl(cliente);
    }
}
