package co.com.asgard.core.service;

import co.com.asgard.core.model.*;
import co.com.asgard.core.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DespachoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ListaDespachoRepository listaDespachoRepository;

    @Mock
    private StockService stockService;

    @InjectMocks
    private DespachoService despachoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarProducto_guardaCorrectamente() {
        Producto producto = new Producto();
        producto.setNombre("Test Producto");
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = despachoService.registrarProducto(producto);

        assertNotNull(resultado);
        assertEquals("Test Producto", resultado.getNombre());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void crearListaDespacho_exitoso() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(20);

        DetalleDespacho detalle = new DetalleDespacho();
        detalle.setProducto(producto);
        detalle.setCantidad(10);

        ListaDespacho lista = new ListaDespacho();
        lista.setDestino("Almacén");

        when(stockService.verificarStock(1L, 10)).thenReturn(true);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(listaDespachoRepository.save(any(ListaDespacho.class))).thenReturn(lista);

        ListaDespacho resultado = despachoService.crearListaDespacho("Almacén", Collections.singletonList(detalle));

        assertNotNull(resultado);
        assertEquals("Almacén", resultado.getDestino());
        verify(stockService, times(1)).reducirStock(1L, 10);
    }

    @Test
    void crearListaDespacho_fallaPorStockInsuficiente() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(5);

        DetalleDespacho detalle = new DetalleDespacho();
        detalle.setProducto(producto);
        detalle.setCantidad(10);

        when(stockService.verificarStock(1L, 10)).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> {
            despachoService.crearListaDespacho("Almacén", Collections.singletonList(detalle));
        });

        assertEquals("Stock insuficiente para producto ID: 1", exception.getMessage());
    }
}