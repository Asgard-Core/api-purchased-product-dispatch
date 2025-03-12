package co.com.asgard.core.model;

import co.com.asgard.core.enums.StatusProduct;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(name = "quantity_available", nullable = false)
    private int quantityAvailable;

    private String address;

    @Enumerated(EnumType.STRING)
    private StatusProduct status;

}