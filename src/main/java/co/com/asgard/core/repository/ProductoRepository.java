package co.com.asgard.core.repository;

import co.com.asgard.core.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigoOrNombre(String codigo, String nombre);

    Producto findByCodigo(String codigo);

}