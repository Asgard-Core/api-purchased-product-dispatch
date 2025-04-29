package co.com.asgard.core.dto;

import java.time.LocalDateTime;

public class AssignedOrderDTO {

    private Long orderId;
    private String destinationAddress;
    private String orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime shippedAt;

    // Constructor
    public AssignedOrderDTO(Long orderId, String destinationAddress, String orderStatus,
                            LocalDateTime createdAt, LocalDateTime shippedAt) {
        this.orderId = orderId;
        this.destinationAddress = destinationAddress;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.shippedAt = shippedAt;
    }

    // Getters y Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getShippedAt() {
        return shippedAt;
    }

    public void setShippedAt(LocalDateTime shippedAt) {
        this.shippedAt = shippedAt;
    }
}