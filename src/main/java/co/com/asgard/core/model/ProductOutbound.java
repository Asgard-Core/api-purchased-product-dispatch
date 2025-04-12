package co.com.asgard.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
}