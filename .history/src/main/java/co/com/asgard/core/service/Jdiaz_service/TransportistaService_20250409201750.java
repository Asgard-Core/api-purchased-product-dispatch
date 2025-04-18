package co.com.asgard.core.service.Jdiaz_service;

import co.com.asgard.core.model.jdiaz_models.*;

import co.com.asgard.core.repository.jdiaz_repositorio.*;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportistaService {

    private final TransportistaRepository transportistaRepository;


    public TransportistaService(TransportistaRepository transportistaRepository) {
        this.transportistaRepository = transportistaRepository;
    }

    public List<transportistas> obtenerTodos() {
        return transportistaRepository.findAll();
    }

    public Optional<transportistas> obtenerPorId(Long id) {
        return transportistaRepository.findById(id);
    }

    public transportistas guardar(transportistas transportista) {
        return transportistaRepository.save(transportista);
    }

    public void eliminar(Long id) {
        transportistaRepository.deleteById(id);
    }
}