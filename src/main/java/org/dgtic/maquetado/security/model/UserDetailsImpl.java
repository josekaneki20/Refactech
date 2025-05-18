package org.dgtic.maquetado.security.model;

import org.dgtic.maquetado.model.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    
    private  Cliente cliente;

    public UserDetailsImpl(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + cliente.getRol().getNombre()));
    }

    @Override
    public String getPassword() {
        return cliente.getContrasena(); // Asegúrate de usar el nombre correcto
    }

    @Override
    public String getUsername() {
        return cliente.getCorreoElectronico();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public Cliente getCliente() {
        return cliente;
    }
}
