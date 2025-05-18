package org.dgtic.maquetado.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Pago")
    private Long idPago;
    @Column(name="fecha_pago")
    private String fechaPago;
    @Column(name="metodo_pago")
    private String metodoPago;
    @Column(name = "monto")
    private Double monto;
    @Transient
    @NotBlank(message = "El número de tarjeta es obligatorio")
    @Pattern(regexp = "^[0-9]{13,19}$", message = "El número de tarjeta debe tener entre 13 y 19 dígitos")
    private String numeroTarjeta;

    @Transient
    @NotBlank(message = "El nombre del titular es obligatorio")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,}$", message = "Nombre inválido, solo letras y espacios")
    private String nombreTitular;

    @Transient
    @NotBlank(message = "El CVV es obligatorio")
    @Pattern(regexp = "^[0-9]{3,4}$", message = "El CVV debe tener 3 o 4 dígitos")
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "id_Pedido", nullable = false)
    private Pedido pedido;
}

