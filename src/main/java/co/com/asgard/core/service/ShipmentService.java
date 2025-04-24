package co.com.asgard.core.service;
import co.com.asgard.core.dto.AssignedOrderDTO;
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
}