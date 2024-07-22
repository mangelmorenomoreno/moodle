package com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.repository;


import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.GrupoSemilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * GrupoSemillaRepository.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
public interface GrupoSemillaRepository extends JpaRepository<GrupoSemilla, Long> {

}
