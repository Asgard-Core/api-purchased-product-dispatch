package co.com.asgard.core.model;

import co.com.asgard.core.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_outbound")
@Getter
@Setter
@RequiredArgsConstructor
public class ProductOutbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeRegister; // Número de registro único generado

    @ManyToOne
    private Product product;

    private Integer quantity;

    private String destination;

    private LocalDate date;

    @ManyToOne
    private AppUser responsible;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // Estado del pedido

    private LocalDateTime statusUpdatedAt;  // Fecha y hora de la última actualización del estado

    // Método para actualizar el estado y la fecha de actualización en un solo paso
    public void actualizarEstado(OrderStatus nuevoEstado) {
        this.status = nuevoEstado;
        this.statusUpdatedAt = LocalDateTime.now();  // Asignamos la fecha y hora actual
    }
}
