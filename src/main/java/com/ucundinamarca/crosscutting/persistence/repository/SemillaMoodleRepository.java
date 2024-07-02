package com.ucundinamarca.crosscutting.persistence.repository;

import com.ucundinamarca.crosscutting.persistence.entity.camposdeaprendizaje.SemillaMoodle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemillaMoodleRepository extends JpaRepository<SemillaMoodle, Long> {
}
