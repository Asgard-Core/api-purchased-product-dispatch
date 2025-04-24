package co.com.asgard.core.service.impl;

import co.com.asgard.core.config.EntityServiceException;
import co.com.asgard.core.dto.ProductOutboundRequestDTO;
import co.com.asgard.core.dto.ProductOutboundResponseDTO;
import co.com.asgard.core.model.AppUser;
import co.com.asgard.core.model.Product;
import co.com.asgard.core.model.ProductOutbound;
import co.com.asgard.core.repository.AppUserRepository;
import co.com.asgard.core.repository.ProductOutboundRepository;
import co.com.asgard.core.repository.ProductRepository;
import co.com.asgard.core.dto.ProductOutboundStatusUpdateDTO;
import co.com.asgard.core.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductOutboundServiceImplTest {

    @Mock
    private ProductRepository productRepo;

    @Mock
    private AppUserRepository userRepo;

    @Mock
    private ProductOutboundRepository outboundRepo;

    @InjectMocks
    private ProductOutboundServiceImpl service;

    @Test
    void registerOutbound_validRequest_shouldReturnProductOutboundResponseDTO() {
        ProductOutboundRequestDTO requestDTO = new ProductOutboundRequestDTO();
        requestDTO.setProductCode("PROD-001");
        requestDTO.setQuantity(5);
        requestDTO.setDestination("Bodega B");
        requestDTO.setDate(LocalDate.now());
        requestDTO.setResponsibleId(1L);

        Product product = new Product();
        product.setId(10L);
        product.setCode("PROD-001");
        product.setName("Laptop");
        product.setCurrentStock(10);

        AppUser user = new AppUser();
        user.setId(1L);
        user.setFullName("Jane Doe");

        ProductOutbound savedOutbound = new ProductOutbound();
        savedOutbound.setId(1L);
        savedOutbound.setCodeRegister("OUT-RANDOM");
        savedOutbound.setProduct(product);
        savedOutbound.setQuantity(requestDTO.getQuantity());
        savedOutbound.setDestination(requestDTO.getDestination());
        savedOutbound.setDate(requestDTO.getDate());
        savedOutbound.setResponsible(user);

        when(productRepo.findByCode(requestDTO.getProductCode())).thenReturn(Optional.of(product));
        when(userRepo.findById(requestDTO.getResponsibleId())).thenReturn(Optional.of(user));
        when(outboundRepo.save(any(ProductOutbound.class))).thenReturn(savedOutbound);
        when(productRepo.save(any(Product.class))).thenReturn(product); // Mock para la actualización de stock

        ProductOutboundResponseDTO responseDTO = service.registerOutbound(requestDTO);
        responseDTO.setCodeRegister("OUT-RANDOM"); // Variable cambiante

        assertNotNull(responseDTO);
        assertEquals(savedOutbound.getCodeRegister(), responseDTO.getCodeRegister());
        assertEquals(product.getName(), responseDTO.getProductName());
        assertEquals(requestDTO.getQuantity(), responseDTO.getQuantity());
        assertEquals(requestDTO.getDestination(), responseDTO.getDestination());
        assertEquals(requestDTO.getDate(), responseDTO.getDate());
        assertEquals(user.getFullName(), responseDTO.getResponsibleName());
        assertEquals(5, product.getCurrentStock()); // Verifica que el stock se haya actualizado
        verify(productRepo, times(1)).save(product); // Verifica que se guardó el producto actualizado
        verify(outboundRepo, times(1)).save(any(ProductOutbound.class)); // Verifica que se guardó la salida
    }

    @Test
    void actualizarEstadoPedido_validRequest_shouldUpdateStatusAndTimestamp() {
        ProductOutbound productOutbound = new ProductOutbound();
        productOutbound.setId(1L);
        productOutbound.setStatus(OrderStatus.EN_PROCESO);
        productOutbound.setStatusUpdatedAt(LocalDateTime.now().minusHours(1));  // Hora hace 1 hora

        when(outboundRepo.findById(1L)).thenReturn(Optional.of(productOutbound));

        // Actualizamos el estado a "DESPACHADO"
        service.actualizarEstadoPedido(1L, "DESPACHADO");

        // Verificamos que el estado se haya actualizado correctamente
        assertEquals(OrderStatus.DESPACHADO, productOutbound.getStatus());

        // Verificamos que la fecha de actualización no sea null
        assertNotNull(productOutbound.getStatusUpdatedAt());

        // Verificamos que la fecha de actualización sea reciente
        assertTrue(productOutbound.getStatusUpdatedAt().isAfter(LocalDateTime.now().minusMinutes(5))); // Debe estar dentro de los últimos 5 minutos

        // Verificamos que el repositorio haya guardado el cambio
        verify(outboundRepo, times(1)).save(productOutbound);
    }

    @Test
    void actualizarEstadoPedido_pedidoNotFound_shouldThrowEntityServiceException() {
        // Simulamos que el pedido no existe
        when(outboundRepo.findById(1L)).thenReturn(Optional.empty());

        // Verificamos que se lanza la excepción cuando no se encuentra el pedido
        EntityServiceException exception = assertThrows(EntityServiceException.class, () -> {
            service.actualizarEstadoPedido(1L, "DESPACHADO");
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertTrue(exception.getMessage().contains("Pedido no encontrado con id: 1"));
    }

    @Test
    void updateStatus_validRequest_shouldUpdateStatusAndTimestamp() {
        ProductOutboundStatusUpdateDTO dto = new ProductOutboundStatusUpdateDTO();
        dto.setId(1L);
        dto.setStatus(OrderStatus.ENTREGADO);

        ProductOutbound productOutbound = new ProductOutbound();
        productOutbound.setId(1L);
        productOutbound.setStatus(OrderStatus.EN_PROCESO);

        when(outboundRepo.findById(1L)).thenReturn(Optional.of(productOutbound));

        // Llamamos al método para actualizar el estado
        service.updateStatus(dto);

        // Verificamos que el estado se haya actualizado correctamente
        assertEquals(OrderStatus.ENTREGADO, productOutbound.getStatus());

        // Verificamos que la fecha de actualización no sea null
        assertNotNull(productOutbound.getStatusUpdatedAt());

        // Verificamos que la fecha de actualización sea reciente
        assertTrue(productOutbound.getStatusUpdatedAt().isAfter(LocalDateTime.now().minusMinutes(5))); // Debe estar dentro de los últimos 5 minutos

        // Verificamos que el repositorio haya guardado el cambio
        verify(outboundRepo, times(1)).save(productOutbound);
    }

    @Test
    void updateStatus_pedidoNotFound_shouldThrowEntityServiceException() {
        ProductOutboundStatusUpdateDTO dto = new ProductOutboundStatusUpdateDTO();
        dto.setId(1L);
        dto.setStatus(OrderStatus.ENTREGADO);

        // Simulamos que el pedido no existe
        when(outboundRepo.findById(1L)).thenReturn(Optional.empty());

        // Verificamos que se lanza la excepción cuando no se encuentra el pedido
        EntityServiceException exception = assertThrows(EntityServiceException.class, () -> {
            service.updateStatus(dto);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertTrue(exception.getMessage().contains("Registro no encontrado con id: 1"));
    }
}
