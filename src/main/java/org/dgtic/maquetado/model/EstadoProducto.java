package org.dgtic.maquetado.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "estado_producto")
public class EstadoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Estado")
    private Long idEstado;

    private String descripcion;

    public EstadoProducto(String descripcion) {
        this.descripcion = descripcion;
    }
}
