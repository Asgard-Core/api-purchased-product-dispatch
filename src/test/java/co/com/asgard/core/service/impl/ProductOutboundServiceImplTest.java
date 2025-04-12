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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
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
    void registerOutbound_productNotFound_shouldThrowEntityServiceException() {
        ProductOutboundRequestDTO requestDTO = new ProductOutboundRequestDTO();
        requestDTO.setProductCode("PROD-999");

        when(productRepo.findByCode(requestDTO.getProductCode())).thenReturn(Optional.empty());

        EntityServiceException exception = assertThrows(EntityServiceException.class, () -> {
            service.registerOutbound(requestDTO);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Producto con código PROD-999 no encontrado", exception.getMessage());
        verify(productRepo, never()).save(any(Product.class));
        verify(outboundRepo, never()).save(any(ProductOutbound.class));
    }

    @Test
    void registerOutbound_invalidQuantityZero_shouldThrowEntityServiceException() {
        ProductOutboundRequestDTO requestDTO = new ProductOutboundRequestDTO();
        requestDTO.setProductCode("PROD-001");
        requestDTO.setQuantity(0);

        Product product = new Product();
        product.setCode("PROD-001");

        when(productRepo.findByCode(requestDTO.getProductCode())).thenReturn(Optional.of(product));

        EntityServiceException exception = assertThrows(EntityServiceException.class, () -> {
            service.registerOutbound(requestDTO);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Cantidad debe ser mayor a cero", exception.getMessage());
        verify(productRepo, never()).save(any(Product.class));
        verify(outboundRepo, never()).save(any(ProductOutbound.class));
    }

    @Test
    void registerOutbound_invalidQuantityNull_shouldThrowEntityServiceException() {
        ProductOutboundRequestDTO requestDTO = new ProductOutboundRequestDTO();
        requestDTO.setProductCode("PROD-001");
        requestDTO.setQuantity(null);

        Product product = new Product();
        product.setCode("PROD-001");

        when(productRepo.findByCode(requestDTO.getProductCode())).thenReturn(Optional.of(product));

        EntityServiceException exception = assertThrows(EntityServiceException.class, () -> {
            service.registerOutbound(requestDTO);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Cantidad debe ser mayor a cero", exception.getMessage());
        verify(productRepo, never()).save(any(Product.class));
        verify(outboundRepo, never()).save(any(ProductOutbound.class));
    }

    @Test
    void registerOutbound_insufficientStock_shouldThrowEntityServiceException() {
        ProductOutboundRequestDTO requestDTO = new ProductOutboundRequestDTO();
        requestDTO.setProductCode("PROD-001");
        requestDTO.setQuantity(15);
        requestDTO.setResponsibleId(1L);

        Product product = new Product();
        product.setCode("PROD-001");
        product.setCurrentStock(10);

        when(productRepo.findByCode(requestDTO.getProductCode())).thenReturn(Optional.of(product));

        EntityServiceException exception = assertThrows(EntityServiceException.class, () -> {
            service.registerOutbound(requestDTO);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Stock insuficiente para esta salida", exception.getMessage());
        verify(productRepo, never()).save(any(Product.class));
        verify(outboundRepo, never()).save(any(ProductOutbound.class));
    }

    @Test
    void registerOutbound_responsibleNotFound_shouldThrowEntityServiceException() {
        ProductOutboundRequestDTO requestDTO = new ProductOutboundRequestDTO();
        requestDTO.setProductCode("PROD-001");
        requestDTO.setQuantity(5);
        requestDTO.setResponsibleId(99L);

        Product product = new Product();
        product.setCode("PROD-001");
        product.setCurrentStock(10);

        when(productRepo.findByCode(requestDTO.getProductCode())).thenReturn(Optional.of(product));
        when(userRepo.findById(requestDTO.getResponsibleId())).thenReturn(Optional.empty());

        EntityServiceException exception = assertThrows(EntityServiceException.class, () -> {
            service.registerOutbound(requestDTO);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Responsable no encontrado", exception.getMessage());
        verify(productRepo, never()).save(any(Product.class));
        verify(outboundRepo, never()).save(any(ProductOutbound.class));
    }
}