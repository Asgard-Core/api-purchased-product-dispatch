package co.com.asgard.core.repository.jdiaz_repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.asgard.core.model.jdiaz_models.*;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}