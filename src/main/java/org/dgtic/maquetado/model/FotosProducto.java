package org.dgtic.maquetado.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fotos_producto")
public class FotosProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Foto")
    private Long idFoto;

    @Column(name="urlFoto")
    private String urlFoto;

    @ManyToOne
    @JoinColumn(name = "id_Producto", nullable = false)
    private Producto producto;


    public FotosProducto() {
    }

    public FotosProducto(String urlFoto, Producto producto) {
        this.urlFoto = urlFoto;
        this.producto = producto;
    }

    public Long getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Long idFoto) {
        this.idFoto = idFoto;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
