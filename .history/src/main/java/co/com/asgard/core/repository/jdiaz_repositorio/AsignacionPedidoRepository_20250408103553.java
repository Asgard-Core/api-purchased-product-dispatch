package com.jhonny.api.repositories.jdiaz_repositorio;


import com.jhonny.api.models.jdiaz_models.asignacionesPedidos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionPedidoRepository extends JpaRepository<asignacionesPedidos, Long> {
}