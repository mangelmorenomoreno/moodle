package com.ucundinamarca.crosscutting.persistence.repository;

import com.ucundinamarca.crosscutting.persistence.entity.camposdeaprendizaje.NotasMoodle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotasMoodleRepository extends JpaRepository<NotasMoodle, Long> {
}
