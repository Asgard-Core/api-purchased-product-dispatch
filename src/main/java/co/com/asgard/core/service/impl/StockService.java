package co.com.asgard.core.service.impl;

import co.com.asgard.core.model.ConsultaHistorial;
import co.com.asgard.core.util.EstadoProducto;
import co.com.asgard.core.model.Producto;
import co.com.asgard.core.repository.ConsultaHistorialRepository;
import co.com.asgard.core.repository.ProductoRepository;
import co.com.asgard.core.service.IStockService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class StockService implements IStockService {

    private final ProductoRepository productoRepository;
    private final ConsultaHistorialRepository historialRepository;

    @Autowired
    public StockService(ProductoRepository productoRepository, ConsultaHistorialRepository historialRepository) {
        this.productoRepository = productoRepository;
        this.historialRepository = historialRepository;
    }

    public Producto buscarProducto(String query) {
        return productoRepository.findByCodigoOrNombre(query, query)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Producto no encontrado, verifique el código o nombre ingresado."));
    }

    public Producto actualizarStock(String codigo, int cantidadNueva) {
        Producto producto = productoRepository.findByCodigoOrNombre(codigo, codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se pudo actualizar el stock. Producto no encontrado."));

        producto.setCantidadDisponible(cantidadNueva);
        producto.setEstado(cantidadNueva == 0 ? EstadoProducto.AGOTADO : EstadoProducto.DISPONIBLE);

        return productoRepository.save(producto);
    }

    public void guardarConsulta(String usuario, String codigoProducto) {
        // Buscar el producto en la base de datos por código
        Producto producto = productoRepository.findByCodigo(codigoProducto);

        if (producto == null) {
            throw new EntityNotFoundException("Producto no encontrado con código: " + codigoProducto);
        }

        // Crear y guardar la consulta en el historial
        ConsultaHistorial historial = new ConsultaHistorial(producto, usuario, LocalDateTime.now());
        historialRepository.save(historial);
    }

}