package org.dgtic.maquetado.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estado_pedido")
@Setter
@Getter
public class EstadoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_pedido")
    private Long idEstadoPedido;
    @Column(name = "descripcion")
    private String descripcion;



    public EstadoPedido() {
    }


    public EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdEstadoPedido() {
        return idEstadoPedido;
    }

    public void setIdEstadoPedido(Long idEstadoPedido) {
        this.idEstadoPedido = idEstadoPedido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "EstadoPedido{" +
                "idEstadoPedido=" + idEstadoPedido +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
