package co.com.asgard.core.service;

import co.com.asgard.core.model.*;
import co.com.asgard.core.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DespachoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ListaDespachoRepository listaDespachoRepository;

    @Autowired
    private StockService stockService;

    public Producto registrarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public ListaDespacho crearListaDespacho(String destino, List<DetalleDespacho> detalles) throws Exception {
        // Validar stock
        for (DetalleDespacho detalle : detalles) {
            if (!stockService.verificarStock(detalle.getProducto().getId(), detalle.getCantidad())) {
                throw new Exception("Stock insuficiente para producto ID: " + detalle.getProducto().getId());
            }
        }

        // Crear lista de despacho
        ListaDespacho lista = new ListaDespacho();
        lista.setDestino(destino);
        lista.setFechaCreacion(LocalDateTime.now());
        lista.setDetalles(detalles);
        lista.setComprobanteSalida("DESP-" + System.currentTimeMillis() + ".pdf");

        // Reducir stock
        for (DetalleDespacho detalle : detalles) {
            stockService.reducirStock(detalle.getProducto().getId(), detalle.getCantidad());
        }

        return listaDespachoRepository.save(lista);
    }
}