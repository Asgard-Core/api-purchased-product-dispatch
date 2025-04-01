package co.com.asgard.core.controller;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.com.asgard.core.model.transportistas;
import co.com.asgard.core.service.TransportistaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transportistas")
public class TransportistaController {

    private final TransportistaService transportistaService;

    public TransportistaController(TransportistaService transportistaService) {
        this.transportistaService = transportistaService;
    }

    @GetMapping
    public List<transportistas> obtenerTodos() {
        return transportistaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<transportistas> obtenerPorId(@PathVariable Long id) {
        Optional<transportistas> transportista = transportistaService.obtenerPorId(id);
        return transportista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public transportistas crear(@RequestBody transportistas transportista) {
        return transportistaService.guardar(transportista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<transportistas> actualizar(@PathVariable Long id, @RequestBody transportistas transportista) {
        if (!transportistaService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        transportista.setId(id);
        return ResponseEntity.ok(transportistaService.guardar(transportista));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!transportistaService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        transportistaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
///sdsidhuiasudhiaushdia