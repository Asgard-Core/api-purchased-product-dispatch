package co.com.asgard.core.controller;

import co.com.asgard.core.dto.ShippingLabelRequestDTO;
import co.com.asgard.core.dto.ShippingLabelResponseDTO;
import co.com.asgard.core.service.ShippingLabelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipping-labels")
public class ShippingLabelController {

    private final ShippingLabelService service;

    public ShippingLabelController(ShippingLabelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ShippingLabelResponseDTO> createLabel(@RequestBody ShippingLabelRequestDTO request) {
        ShippingLabelResponseDTO response = service.generateLabel(request);
        return ResponseEntity.ok(response);
    }
}