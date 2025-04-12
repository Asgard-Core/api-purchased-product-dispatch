package co.com.asgard.core.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductOutboundRequestDTO {
    private String productCode;
    private Integer quantity;
    private String destination;
    private LocalDate date;
    private Long responsibleId;
}