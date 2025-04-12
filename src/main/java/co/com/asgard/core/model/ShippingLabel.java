package co.com.asgard.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shipping_label")
@Getter
@Setter
@RequiredArgsConstructor
public class ShippingLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "tracking_code")
    private String trackingCode;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "barcode", nullable = false)
    private String barcode;

}