package co.com.asgard.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "incidents")
@Getter
@Setter
@RequiredArgsConstructor
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // Ej: "Retraso", "Dirección incorrecta", etc.

    private String cause;

    private String resolution;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}