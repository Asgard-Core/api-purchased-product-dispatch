package com.jhonny.api.controller.jdiaz_controller;



import com.jhonny.api.models.jdiaz_models.asignacionesPedidos;

import com.jhonny.api.services.Jdiaz_service.AsignacionPedidoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionPedidoController {

    private final AsignacionPedidoService asignacionPedidoService;

    public AsignacionPedidoController(AsignacionPedidoService asignacionPedidoService) {
        this.asignacionPedidoService = asignacionPedidoService;
    }

    @GetMapping
    public List<asignacionesPedidos> obtenerTodos() {
        return asignacionPedidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<asignacionesPedidos> obtenerPorId(@PathVariable Long id) {
        Optional<asignacionesPedidos> asignacion = asignacionPedidoService.obtenerPorId(id);
        return asignacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public asignacionesPedidos crear(@RequestBody asignacionesPedidos asignacionPedido) {
        return asignacionPedidoService.guardar(asignacionPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<asignacionesPedidos> actualizar(@PathVariable Long id, @RequestBody asignacionesPedidos asignacionPedido) {
        if (!asignacionPedidoService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        asignacionPedido.setId(id);
        return ResponseEntity.ok(asignacionPedidoService.guardar(asignacionPedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!asignacionPedidoService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        asignacionPedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}