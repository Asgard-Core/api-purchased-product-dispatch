package co.com.asgard.core.service;
import co.com.asgard.core.dto.AssignedOrderDTO;
import co.com.asgard.core.repository.CustomerOrderRepository;
import co.com.asgard.core.repository.OrderStatusRepository;
import co.com.asgard.core.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    public List<AssignedOrderDTO> getAssignedOrders(Long carrierId) {
        return shipmentRepository.findAssignedOrdersByCarrier(carrierId);
    }

    @Autowired
private CustomerOrderRepository customerOrderRepository;

@Autowired
private OrderStatusRepository orderStatusRepository;

public boolean updateOrderStatus(Long orderId, Long statusId) {
    var optionalOrder = customerOrderRepository.findById(orderId);
    var optionalStatus = orderStatusRepository.findById(statusId);

    if (optionalOrder.isEmpty() || optionalStatus.isEmpty()) {
        return false;
    }

    var order = optionalOrder.get();
    order.setStatus(optionalStatus.get());
    customerOrderRepository.save(order);
    return true;
}

}