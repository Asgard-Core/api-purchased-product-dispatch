package co.com.asgard.core.dto;

public class UpdateOrderStatusRequest {
    private Long orderId;
    private Long statusId;

    public UpdateOrderStatusRequest() {}

    public UpdateOrderStatusRequest(Long orderId, Long statusId) {
        this.orderId = orderId;
        this.statusId = statusId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
