package co.com.asgard.core.service;

import co.com.asgard.core.model.Producto;
import co.com.asgard.core.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void verificarStock_devuelveTrueCuandoHayStock() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(15);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        boolean resultado = stockService.verificarStock(1L, 10);

        assertTrue(resultado);
    }

    @Test
    void reducirStock_actualizaStockCorrectamente() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(20);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        stockService.reducirStock(1L, 10);

        assertEquals(10, producto.getStock());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void reducirStock_lanzaExcepcionPorStockInsuficiente() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(5);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            stockService.reducirStock(1L, 10);
        });

        assertEquals("Stock insuficiente", exception.getMessage());
    }
}