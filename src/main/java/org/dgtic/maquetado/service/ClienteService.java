package org.dgtic.maquetado.service;

import org.dgtic.maquetado.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ClienteService {

    void guardarCliente(Cliente cliente);

    boolean existeNumero(String numero);
    boolean existeCorreo(String correo);

    Cliente obtenerClienteporId(Long idCliente);

    List<Cliente> obtenerTodoLosClientes();

    void actualizarCliente(Cliente cliente);

    void eliminarCliente(Long id);


}
