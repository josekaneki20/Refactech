package org.dgtic.maquetado.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_carrito")
public class ItemCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_carrito")
    private Long idItemCarrito;
    @ManyToOne
    @JoinColumn(name = "id_Producto", nullable = false)
    private Producto producto;
    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "id_carrito", nullable = false)
    private Carrito carrito;

    public ItemCarrito() {
    }

    public Long getIdItemCarrito() {
        return idItemCarrito;
    }

    public void setIdItemCarrito(Long idItemCarrito) {
        this.idItemCarrito = idItemCarrito;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
}
