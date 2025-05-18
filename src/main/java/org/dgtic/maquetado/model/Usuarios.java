package org.dgtic.maquetado.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Usuario")
    private Long idUsuario;

    private String nombre;
    private String correo;
    private String contraseña;

    @ManyToOne
    @JoinColumn(name = "id_Rol", nullable = false)
    private Roles rol;
}
