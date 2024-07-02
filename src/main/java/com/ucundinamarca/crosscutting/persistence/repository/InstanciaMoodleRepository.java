package com.ucundinamarca.crosscutting.persistence.repository;

import com.ucundinamarca.crosscutting.persistence.entity.camposdeaprendizaje.InstanciaMoodle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanciaMoodleRepository extends JpaRepository<InstanciaMoodle, Long> {
}
