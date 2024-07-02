package com.ucundinamarca.crosscutting.persistence.repository;

import com.ucundinamarca.crosscutting.persistence.entity.Comentario;
import com.ucundinamarca.crosscutting.persistence.entity.Publicacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad ComentarioDto.
 * Facilita operaciones CRUD y permite definir consultas específicas relacionadas con Comentarios.
 *
 * @author miguel.moreno
 * @version 1.0
 */

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

  List<Comentario> findByPublicacion(Publicacion publicacion);

  List<Comentario> findByCommentId(Integer commentId);
}
