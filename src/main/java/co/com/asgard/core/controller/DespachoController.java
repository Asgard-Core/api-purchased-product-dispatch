package co.com.asgard.core.controller;

import co.com.asgard.core.service.ProductOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/despacho")
public class DespachoController {

    @Autowired
    private ProductOutboundService productOutboundService;

    // Endpoint para actualizar el estado de un pedido
    @PutMapping("/pedido/{id}/estado")
    public ResponseEntity<Void> actualizarEstadoPedido(
            @PathVariable Long id, 
            @RequestBody String nuevoEstado) {
        productOutboundService.actualizarEstadoPedido(id, nuevoEstado);
        return ResponseEntity.ok().build();
    }
}
