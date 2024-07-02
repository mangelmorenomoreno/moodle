package com.ucundinamarca.crosscutting.persistence.repository;

import com.ucundinamarca.crosscutting.persistence.entity.camposdeaprendizaje.MatriculaMoodle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaMoodleRepository extends JpaRepository<MatriculaMoodle, Long> {
}
