package com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.repository;

import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.NotasMoodle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * NotasMoodleRepository.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
public interface NotasMoodleRepository extends JpaRepository<NotasMoodle, Long> {
}
