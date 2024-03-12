package com.prueba.carvajal.modules.comentario.dataproviders;

import com.prueba.carvajal.crosscutting.persistence.entity.Comentario;
import com.prueba.carvajal.crosscutting.persistence.entity.Publicacion;
import java.util.List;

/**
 * IcomentarioDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
public interface IcomentarioDataProviders {

  Boolean delete(List<Comentario> comentario);

  Boolean delete(Comentario comentario);

  Comentario save(Comentario comentario);

  Comentario update(Comentario comentario);

  Comentario findCommentId(Integer commentId);

  List<Comentario> findPublicacion(Publicacion publicacion);

  List<Comentario> findAll();





}
