package co.com.asgard.core.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductOutboundResponseDTO {
    private String codeRegister;
    private String productName;
    private Integer quantity;
    private String destination;
    private LocalDate date;
    private String responsibleName;
}