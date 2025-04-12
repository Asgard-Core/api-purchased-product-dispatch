package co.com.asgard.core.dto;

import lombok.Data;

@Data
public class ShippingLabelRequestDTO {
    private Long orderId; // importante para vincular a CustomerOrder
    private String shippingAddress;
    private String productCode;
    private String productName;
    private Integer quantity;
}