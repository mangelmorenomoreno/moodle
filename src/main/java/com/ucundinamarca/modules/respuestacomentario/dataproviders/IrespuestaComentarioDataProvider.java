package com.ucundinamarca.modules.respuestacomentario.dataproviders;

import com.ucundinamarca.crosscutting.persistence.entity.Comentario;
import com.ucundinamarca.crosscutting.persistence.entity.RespuestaComentario;

import java.util.List;

/**
 * RespuestaComentarioController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
public interface IrespuestaComentarioDataProvider {

  RespuestaComentario save(RespuestaComentario comentario);

  RespuestaComentario update(RespuestaComentario comentario);

  Boolean delete(List<RespuestaComentario> respuestaComentario);

  List<RespuestaComentario> findById(Integer respuestaId);

  List<RespuestaComentario> findByAll();


  List<RespuestaComentario> findByComentario(Comentario comentario);


}
