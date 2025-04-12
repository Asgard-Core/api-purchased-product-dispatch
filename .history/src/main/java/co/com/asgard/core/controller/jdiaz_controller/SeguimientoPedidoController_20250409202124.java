package co.com.asgard.core.controller.jdiaz_controller;


import co.com.asgard.core.model.jdiaz_models.*;

import co.com.asgard.core.service.Jdiaz_service.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seguimiento")
public class SeguimientoPedidoController {

    private final SeguimientoPedidoService seguimientoPedidoService;

    public SeguimientoPedidoController(SeguimientoPedidoService seguimientoPedidoService) {
        this.seguimientoPedidoService = seguimientoPedidoService;
    }

    @GetMapping
    public List<seguimientoPedidos> obtenerTodos() {
        return seguimientoPedidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<seguimientoPedidos> obtenerPorId(@PathVariable Long id) {
        Optional<seguimientoPedidos> seguimiento = seguimientoPedidoService.obtenerPorId(id);
        return seguimiento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public seguimientoPedidos crear(@RequestBody seguimientoPedidos seguimientoPedido) {
        return seguimientoPedidoService.guardar(seguimientoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<seguimientoPedidos> actualizar(@PathVariable Long id, @RequestBody seguimientoPedidos seguimientoPedido) {
        if (!seguimientoPedidoService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        seguimientoPedido.setId(id);
        return ResponseEntity.ok(seguimientoPedidoService.guardar(seguimientoPedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!seguimientoPedidoService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        seguimientoPedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
