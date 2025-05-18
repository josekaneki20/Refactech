package org.dgtic.maquetado.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Pedido")
    private Long idPedido;
    @Column(name = "total_compra")
    private Double totalCompra;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "id_Cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_estado_pedido", nullable = false)
    private EstadoPedido idEstadoPedido;
    @ManyToOne
    @JoinColumn(name="id_carrito",nullable=false)
    private Carrito carrito;

    public Pedido() {
    }





}
