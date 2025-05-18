package org.dgtic.maquetado.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "marca")

public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Marca")
    private Long idMarca;

    @OneToMany(mappedBy = "marca",cascade = CascadeType.ALL)
    private List<Producto> productos= new ArrayList<>();

    @Column(name = "marca",nullable = false,unique = true)
    private String marca;

    public Marca() {
    }

    public Marca(List<Producto> productos, String marca) {
        this.productos = productos;
        this.marca = marca;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}

