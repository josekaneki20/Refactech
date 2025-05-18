package org.dgtic.maquetado.model;

import jakarta.persistence.*;

@Entity
@Table(name = "envios")

@NamedQuery(
        name = "Envio.findByClienteNombre",
        query = "SELECT e FROM Envio e JOIN e.pedido p JOIN p.cliente c WHERE c.nombre = :nombreCliente"
)
@NamedQuery(name = "Envio.findByEmpresaRecolectora",
        query = "SELECT e FROM Envio e WHERE e.empresaRecolectora = :empresa")

public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Envio")
    private Long idEnvio;
    @ManyToOne
    @JoinColumn(name = "id_Pedido", nullable = false)
    private Pedido pedido;
    private String direccionEnvio;
    private String empresaRecolectora;


    public Envio() {
    }

    public Envio(Pedido pedido, String direccionEnvio, String empresaRecolectora) {
        this.pedido = pedido;
        this.direccionEnvio = direccionEnvio;
        this.empresaRecolectora = empresaRecolectora;
    }


    public Long getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Long idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public String getEmpresaRecolectora() {
        return empresaRecolectora;
    }

    public void setEmpresaRecolectora(String empresaRecolectora) {
        this.empresaRecolectora = empresaRecolectora;
    }

    @Override
    public String toString() {
        return "Envio{" +
                "idEnvio=" + idEnvio +
                ", pedido=" + pedido +
                ", direccionEnvio='" + direccionEnvio + '\'' +
                ", empresaRecolectora='" + empresaRecolectora + '\'' +
                '}';
    }
}

