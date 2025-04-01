package co.com.asgard.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.asgard.core.model.transportistas;

@Repository
public interface TransportistaRepository extends JpaRepository<transportistas, Long> {
}