package org.dgtic.maquetado.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.dgtic.maquetado.validation.NoEspacioNoVacio;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@Setter

@AllArgsConstructor
@Entity(name = "clientes")
public class Cliente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Cliente")
    private Long idCliente;
    @NotBlank(message = "El nombre no puede estar vacío ni contener solo espacios.")
    @NoEspacioNoVacio
    @Column(name = "nombre",nullable = false)
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacío ni contener solo espacios.")
    @NoEspacioNoVacio
    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;
    @NotBlank(message = "El apellido materno no puede estar vacío.")
    @NoEspacioNoVacio
    @Column(name = "apellido_materno", nullable = false)
    private String apellidoMaterno;
    @Range(min=18,max=70)
    private Integer edad;
    @NotNull(message = "El sexo es obligatorio.")
    private char sexo;
    @NotBlank(message = "El número de teléfono es obligatorio.")
    @Pattern(regexp = "^[0-9]{10}$", message = "El número debe tener 10 dígitos.")
    private String numero;
    @NoEspacioNoVacio
    @Email
    @Column(name = "correo_electronico", nullable = false)
    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Debe ser un correo válido.")
    private String correoElectronico;
    @NoEspacioNoVacio
    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String contrasena;

    @NotBlank(message = "La dirección es obligatoria.")
    @NoEspacioNoVacio
    private String direccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id", nullable = false)
    private Roles rol;

    public Cliente() {
    }

    public Cliente(String nombre, String apellidoPaterno, String apellidoMaterno, Integer edad, char sexo, String numero, String correoElectronico, String contrasena, String direccion) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.edad = edad;
        this.sexo = sexo;
        this.numero = numero;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.direccion = direccion;
    }
}
