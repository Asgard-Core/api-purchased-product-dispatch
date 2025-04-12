package com.jhonny.api.repositories.jdiaz_repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jhonny.api.models.jdiaz_models.transportistas;

@Repository
public interface TransportistaRepository extends JpaRepository<transportistas, Long> {
}