package co.com.asgard.core.service.impl;

import co.com.asgard.core.dto.ShippingLabelRequestDTO;
import co.com.asgard.core.dto.ShippingLabelResponseDTO;
import co.com.asgard.core.model.CustomerOrder;
import co.com.asgard.core.model.ShippingLabel;
import co.com.asgard.core.repository.CustomerOrderRepository;
import co.com.asgard.core.repository.ShippingLabelRepository;
import co.com.asgard.core.service.ShippingLabelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingLabelServiceImpl implements ShippingLabelService {

    private final ShippingLabelRepository labelRepository;
    private final CustomerOrderRepository orderRepository;

    public ShippingLabelResponseDTO generateLabel(ShippingLabelRequestDTO dto) {
        CustomerOrder order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        ShippingLabel label = new ShippingLabel();
        label.setOrder(order);
        label.setShippingAddress(dto.getShippingAddress());
        label.setProductCode(dto.getProductCode());
        label.setProductName(dto.getProductName());
        label.setQuantity(dto.getQuantity());
        label.setBarcode(generateBarcode(dto.getProductCode()));
        label.setTrackingCode("TRK-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase());

        ShippingLabel saved = labelRepository.save(label);

        ShippingLabelResponseDTO response = new ShippingLabelResponseDTO();
        response.setLabelId(saved.getId());
        response.setClientName(order.getUser().getFullName());
        response.setShippingAddress(saved.getShippingAddress());
        response.setProductCode(saved.getProductCode());
        response.setProductName(saved.getProductName());
        response.setQuantity(saved.getQuantity());
        response.setBarcode(saved.getBarcode());
        response.setTrackingCode(saved.getTrackingCode());
        response.setMessage("Etiqueta generada correctamente");

        return response;
    }

    private String generateBarcode(String productCode) {
        return "BAR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
