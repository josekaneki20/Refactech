package org.dgtic.maquetado.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "movimientos_stock")
public class MovimientosStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Movimientos",nullable = false)
    private Integer idMovimientosStock;
   /* @ManyToOne
    @JoinColumn(name = "id_Produc*/
    @ManyToOne
    @JoinColumn(name = "id_Sucursal")
    private Sucursal idSucursal;
    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "fechaMovimiento", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaMovimiento = LocalDateTime.now();
    @Column(name = "motivo")
    private String motivo;

    public MovimientosStock() {
    }


}
