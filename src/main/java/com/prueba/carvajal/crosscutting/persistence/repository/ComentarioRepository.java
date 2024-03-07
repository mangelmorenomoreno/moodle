package com.prueba.carvajal.crosscutting.persistence.repository;

import com.prueba.carvajal.crosscutting.persistence.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repositorio JPA para la entidad Comentario.
 * Facilita operaciones CRUD y permite definir consultas específicas relacionadas con Comentarios.
 */
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {


}
