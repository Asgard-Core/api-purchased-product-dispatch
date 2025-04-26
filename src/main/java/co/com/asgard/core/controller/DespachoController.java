package co.com.asgard.core.controller;

import co.com.asgard.core.model.*;
import co.com.asgard.core.service.DespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.api.base-path}/despacho")
public class DespachoController {
    
    @Autowired
    private DespachoService despachoService;
    
    @PostMapping("/productos")
    public ResponseEntity<Producto> registrarProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(despachoService.registrarProducto(producto));
    }
    
    @PostMapping("/lista")
    public ResponseEntity<?> crearListaDespacho(@RequestBody CrearListaRequest request) {
        try {
            ListaDespacho lista = despachoService.crearListaDespacho(
                request.getDestino(), 
                request.getDetalles()
            );
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}