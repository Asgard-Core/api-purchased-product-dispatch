package co.com.asgard.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@RequiredArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime deliveryDate;

    private boolean successful;

    private String recipient;

    private String deliveryAddress;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}