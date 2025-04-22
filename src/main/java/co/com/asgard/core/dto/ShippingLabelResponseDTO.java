package co.com.asgard.core.dto;

import lombok.Data;

@Data
public class ShippingLabelResponseDTO {
    private Long labelId;
    private String clientName;
    private String shippingAddress;
    private String productCode;
    private String productName;
    private Integer quantity;
    private String barcode;
    private String trackingCode;
    private String message;
}