package org.dgtic.maquetado.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Inventario", nullable = false)
    private Long idInventario;

    @ManyToOne
    @JoinColumn(name = "id_Producto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_Sucursal", nullable = false)
    private Sucursal sucursal;

    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;

    @Column(name = "fecha_registro")
    private Date fechaRegistro;


    public Inventario() {
    }

    public Inventario(Producto producto, Sucursal sucursal, Integer stockActual, Integer stockMinimo, Date fechaRegistro) {
        this.producto = producto;
        this.sucursal = sucursal;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.fechaRegistro = fechaRegistro;
    }


    public Long getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Long idInventario) {
        this.idInventario = idInventario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "idInventario=" + idInventario +
                ", producto=" + producto +
                ", sucursal=" + sucursal +
                ", stockActual=" + stockActual +
                ", stockMinimo=" + stockMinimo +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
