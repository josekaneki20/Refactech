package org.dgtic.maquetado.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dgtic.maquetado.validation.NoEspacioNoVacio;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Producto",nullable = false)
    private Long idProducto;

    @NoEspacioNoVacio
    @NotBlank(message = "El nombre no puede ser vacio")
    @Column(name = "nombre",nullable = false)
    private String nombre;

    @NoEspacioNoVacio
    @NotBlank(message = "La descripcion no puede ser vacia")
    private String descripcion;

    @NoEspacioNoVacio
    @Column(name = "modelo",nullable = false)
    private String modelo;

    @PositiveOrZero
    @Column(name = "precio",nullable = false)
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "id_Categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_Marca", nullable = false)
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_Estado", nullable = false)
    private EstadoProducto estado;


    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<FotosProducto> fotos;


    public Producto(String nombre, String descripcion, String modelo, Double precio, Categoria categoria, Marca marca, EstadoProducto estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modelo = modelo;
        this.precio = precio;
        this.categoria = categoria;
        this.marca = marca;
        this.estado = estado;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public List<FotosProducto> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotosProducto> fotos) {
        this.fotos = fotos;
    }
}
