package co.com.asgard.core.service.impl;

import co.com.asgard.core.dto.ShippingLabelRequestDTO;
import co.com.asgard.core.dto.ShippingLabelResponseDTO;
import co.com.asgard.core.model.AppUser;
import co.com.asgard.core.model.CustomerOrder;
import co.com.asgard.core.model.OrderStatus;
import co.com.asgard.core.model.ShippingLabel;
import co.com.asgard.core.repository.CustomerOrderRepository;
import co.com.asgard.core.repository.ShippingLabelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShippingLabelServiceImplTest {

    @Mock
    private ShippingLabelRepository labelRepository;

    @Mock
    private CustomerOrderRepository orderRepository;

    @InjectMocks
    private ShippingLabelServiceImpl service;

    @Test
    void generateLabel_existingOrder_shouldReturnShippingLabelResponseDTO() {
        Long orderId = 1L;
        ShippingLabelRequestDTO requestDTO = new ShippingLabelRequestDTO();
        requestDTO.setOrderId(orderId);
        requestDTO.setShippingAddress("Calle Falsa 123");
        requestDTO.setProductCode("PROD-123");
        requestDTO.setProductName("Producto de prueba");
        requestDTO.setQuantity(2);

        AppUser user = new AppUser();
        user.setFullName("John Doe");

        OrderStatus status = new OrderStatus();
        status.setId(1L);
        status.setStatusName("Pendiente");

        CustomerOrder order = new CustomerOrder();
        order.setId(orderId);
        order.setUser(user);
        order.setStatus(status);

        ShippingLabel savedLabel = new ShippingLabel();
        savedLabel.setId(10L);
        savedLabel.setOrder(order);
        savedLabel.setShippingAddress(requestDTO.getShippingAddress());
        savedLabel.setProductCode(requestDTO.getProductCode());
        savedLabel.setProductName(requestDTO.getProductName());
        savedLabel.setQuantity(requestDTO.getQuantity());
        savedLabel.setBarcode("BAR-RANDOM");
        savedLabel.setTrackingCode("TRK-RANDOM");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(labelRepository.save(any(ShippingLabel.class))).thenReturn(savedLabel);

        ShippingLabelResponseDTO responseDTO = service.generateLabel(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(savedLabel.getId(), responseDTO.getLabelId());
        assertEquals(user.getFullName(), responseDTO.getClientName());
        assertEquals(requestDTO.getShippingAddress(), responseDTO.getShippingAddress());
        assertEquals(requestDTO.getProductCode(), responseDTO.getProductCode());
        assertEquals(requestDTO.getProductName(), responseDTO.getProductName());
        assertEquals(requestDTO.getQuantity(), responseDTO.getQuantity());
        assertEquals(savedLabel.getBarcode(), responseDTO.getBarcode());
        assertEquals(savedLabel.getTrackingCode(), responseDTO.getTrackingCode());
        assertEquals("Etiqueta generada correctamente", responseDTO.getMessage());
    }

    @Test
    void generateLabel_nonExistingOrder_shouldThrowEntityNotFoundException() {
        Long orderId = 999L;
        ShippingLabelRequestDTO requestDTO = new ShippingLabelRequestDTO();
        requestDTO.setOrderId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.generateLabel(requestDTO));
    }
}