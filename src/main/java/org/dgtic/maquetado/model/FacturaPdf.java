package org.dgtic.maquetado.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="facturas_pdf")
@Getter
@Setter

public class FacturaPdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Lob
    @Column(name = "archivo_pdf", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] archivoPdf;

    @Column(name = "fecha_guardado")
    private String fechaGuardado;


}
