package org.dgtic.maquetado.model;

import jakarta.persistence.*;
@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_Detalle")
        private Long idDetalle;

        private Integer cantidad;
        private Double subtotal;

        @ManyToOne
        @JoinColumn(name = "id_Pedido", nullable = false)
        private Pedido pedido;

        @ManyToOne
        @JoinColumn(name = "id_Producto", nullable = false)
        private Producto producto;


}

