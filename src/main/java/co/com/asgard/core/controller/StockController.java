package co.com.asgard.core.controller;

import co.com.asgard.core.dto.StockDTO;
import co.com.asgard.core.model.Producto;
import co.com.asgard.core.service.IReportService;
import co.com.asgard.core.service.IStockService;
import co.com.asgard.core.service.impl.StockService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("${app.api.base-path}")
public class StockController {

    private final IStockService stockService;

    @Autowired
    public StockController(IStockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/consultar")
    public ResponseEntity<Producto> buscarProducto(@RequestParam String query) {
        Producto producto = stockService.buscarProducto(query);
        return ResponseEntity.ok(producto);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Producto> actualizarStock(@RequestBody Map<String, Object> body) {
        String codigo = (String) body.get("codigo");
        int cantidadNueva = (int) body.get("cantidadNueva");

        Producto productoActualizado = stockService.actualizarStock(codigo, cantidadNueva);
        return ResponseEntity.ok(productoActualizado);
    }

    @PostMapping("/historial")
    public ResponseEntity<Map<String, String>> guardarHistorial(@RequestBody Map<String, String> body) {
        stockService.guardarConsulta(body.get("usuario"), body.get("productoConsultado"));
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("mensaje", "Historial guardado exitosamente."));
    }
}