package org.dgtic.maquetado.service.impl;

import org.dgtic.maquetado.model.Roles;
import org.dgtic.maquetado.repository.RolesRepository;
import org.dgtic.maquetado.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Roles> obtenerRoles() {
        return rolesRepository.findAll();
    }
}
