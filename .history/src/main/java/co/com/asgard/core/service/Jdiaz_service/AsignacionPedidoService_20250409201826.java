package co.com.asgard.core.service.Jdiaz_service;

import co.com.asgard.core.model.jdiaz_models.*;

import co.com.asgard.core.repository.jdiaz_repositorio.*;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionPedidoService {
    

    private final AsignacionPedidoRepository asignacionPedidoRepository;

    public AsignacionPedidoService(AsignacionPedidoRepository asignacionPedidoRepository) {
        this.asignacionPedidoRepository = asignacionPedidoRepository;
    }

    public List<asignacionesPedidos> obtenerTodos() {
        return asignacionPedidoRepository.findAll();
    }

    public Optional<asignacionesPedidos> obtenerPorId(Long id) {
        return asignacionPedidoRepository.findById(id);
    }

    public asignacionesPedidos guardar(asignacionesPedidos asignacionPedido) {
        return asignacionPedidoRepository.save(asignacionPedido);
    }

    public void eliminar(Long id) {
        asignacionPedidoRepository.deleteById(id);
    }
}
