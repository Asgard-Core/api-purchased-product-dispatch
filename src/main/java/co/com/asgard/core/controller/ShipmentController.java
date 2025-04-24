package co.com.asgard.core.controller;
import co.com.asgard.core.dto.AssignedOrderDTO;
import co.com.asgard.core.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/assigned/{carrierId}")
    public ResponseEntity<List<AssignedOrderDTO>> getAssignedOrders(@PathVariable Long carrierId) {
        List<AssignedOrderDTO> assignedOrders = shipmentService.getAssignedOrders(carrierId);
        return ResponseEntity.ok(assignedOrders);
    }
}
