package com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.repository;

import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.MatriculaMoodle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MatriculaMoodleRepository.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
public interface MatriculaMoodleRepository extends JpaRepository<MatriculaMoodle, Long> {
}
