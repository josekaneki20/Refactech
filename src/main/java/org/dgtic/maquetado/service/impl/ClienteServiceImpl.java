package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.Cliente;
import org.dgtic.maquetado.repository.ClienteRepository;
import org.dgtic.maquetado.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void guardarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public boolean existeNumero(String numero) {
        return clienteRepository.findByNumero(numero).isPresent();
    }

    @Override
    public boolean existeCorreo(String correo) {
        return clienteRepository.findByCorreoElectronico(correo).isPresent();
    }

    @Override
    public Cliente obtenerClienteporId(Long idCliente) {
        Optional<Cliente>cliente=clienteRepository.findById(idCliente);
        return cliente.orElse(null);
    }


    @Override
    public List<Cliente> obtenerTodoLosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void actualizarCliente(Cliente cliente) {

        if (cliente.getIdCliente() != null && clienteRepository.existsById(cliente.getIdCliente())) {
            clienteRepository.save(cliente);
        }

    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
