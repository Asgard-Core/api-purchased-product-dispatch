package co.com.asgard.core.repository.jdiaz_repositorio;

import co.com.asgard.core.model.jdiaz_models.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionPedidoRepository extends JpaRepository<asignacionesPedidos, Long> {
}