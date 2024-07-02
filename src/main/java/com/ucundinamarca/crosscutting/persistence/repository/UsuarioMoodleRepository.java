package com.ucundinamarca.crosscutting.persistence.repository;

import com.ucundinamarca.crosscutting.persistence.entity.camposdeaprendizaje.UsuarioMoodle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioMoodleRepository extends JpaRepository<UsuarioMoodle, Long> {
}
