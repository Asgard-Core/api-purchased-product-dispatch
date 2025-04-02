package co.com.asgard.core.service.impl;

import co.com.asgard.core.enums.StatusProduct;
import co.com.asgard.core.model.Historical;
import co.com.asgard.core.model.Product;
import co.com.asgard.core.repository.HistoricalRepository;
import co.com.asgard.core.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private HistoricalRepository historicalRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    void searchProduct_found() {
        Product product = new Product();
        when(productRepository.findByCodeOrName("123", "123")).thenReturn(Optional.of(product));

        Product result = stockService.searchProduct("123");
        assertNotNull(result);
    }

    @Test
    void searchProduct_notFound() {
        when(productRepository.findByCodeOrName("123", "123")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> stockService.searchProduct("123"));
    }

    @Test
    void updateStock_found() {
        Product product = new Product();
        product.setQuantityAvailable(5);

        when(productRepository.findByCodeOrName("123", "123")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = stockService.updateStock("123", 10);
        assertEquals(10, updatedProduct.getQuantityAvailable());
        assertEquals(StatusProduct.DISPONIBLE, updatedProduct.getStatus());
    }

    @Test
    void updateStock_notFound() {
        when(productRepository.findByCodeOrName("123", "123")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> stockService.updateStock("123", 10));
    }

    @Test
    void saveQuery_productFound() {
        Product product = new Product();
        when(productRepository.findByCode("123")).thenReturn(product);

        stockService.saveQuery("user1", "123");
        verify(historicalRepository, times(1)).save(any(Historical.class));
    }

    @Test
    void saveQuery_productNotFound() {
        when(productRepository.findByCode("123")).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> stockService.saveQuery("user1", "123"));
    }
}
