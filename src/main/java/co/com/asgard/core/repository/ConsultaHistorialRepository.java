package co.com.asgard.core.repository;

import co.com.asgard.core.model.ConsultaHistorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaHistorialRepository extends JpaRepository<ConsultaHistorial, Long> {
}