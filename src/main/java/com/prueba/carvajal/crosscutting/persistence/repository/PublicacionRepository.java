package com.prueba.carvajal.crosscutting.persistence.repository;

import com.prueba.carvajal.crosscutting.persistence.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repositorio JPA para la entidad PublicacionController.
 * Permite realizar operaciones CRUD automáticas y definir consultas personalizadas para Publicaciones.
 */
@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
}
