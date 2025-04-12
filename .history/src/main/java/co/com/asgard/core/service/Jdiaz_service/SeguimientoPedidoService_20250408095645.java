package com.jhonny.api.services.Jdiaz_service;

import com.jhonny.api.models.jdiaz_models.seguimientoPedidos;

import com.jhonny.api.repositories.jdiaz_repositorio.SeguimientoPedidoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeguimientoPedidoService {

    private final SeguimientoPedidoRepository seguimientoPedidoRepository;



    public SeguimientoPedidoService(SeguimientoPedidoRepository seguimientoPedidoRepository) {
        this.seguimientoPedidoRepository = seguimientoPedidoRepository;
    }

    public List<seguimientoPedidos> obtenerTodos() {
        return seguimientoPedidoRepository.findAll();  
    }

    public Optional<seguimientoPedidos> obtenerPorId(Long id) {
        return seguimientoPedidoRepository.findById(id);
    }

    public seguimientoPedidos guardar(seguimientoPedidos seguimientoPedido) {
        return seguimientoPedidoRepository.save(seguimientoPedido);
    }

    public void eliminar(Long id) {
        seguimientoPedidoRepository.deleteById(id);
    }
}
