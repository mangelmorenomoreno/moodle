package com.ucundinamarca.crosscutting.persistence.repository;

import com.ucundinamarca.crosscutting.persistence.entity.Comentario;
import com.ucundinamarca.crosscutting.persistence.entity.RespuestaComentario;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RespuestaComentarioRepository.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
@Repository
public interface RespuestaComentarioRepository extends
    JpaRepository<RespuestaComentario, Integer> {

  List<RespuestaComentario> findByComentario(Comentario comentario);


}
