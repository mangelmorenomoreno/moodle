package com.ucundinamarca.modules.comentario.dataproviders.jpa;

import com.ucundinamarca.crosscutting.persistence.entity.Comentario;
import com.ucundinamarca.crosscutting.persistence.entity.Publicacion;
import com.ucundinamarca.crosscutting.persistence.repository.ComentarioRepository;
import com.ucundinamarca.modules.comentario.dataproviders.IcomentarioDataProviders;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ComentarioDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Log4j2
@Service
public class ComentarioDataProviders implements IcomentarioDataProviders {
  @Autowired
  private ComentarioRepository comentarioRepository;


  @Override
  public Boolean delete(List<Comentario> comentario) {
    for (Comentario comentario1 : comentario) {
      comentarioRepository.delete(comentario1);
    }
    return true;
  }

  /**
   * Elimina un comentario de la base de datos.
   *
   * @param comentario El comentario a eliminar.
   * @return Verdadero si la operación fue exitosa.
   */
  public Boolean delete(Comentario comentario) {

    comentarioRepository.delete(comentario);

    return true;
  }

  @Override
  public Comentario save(Comentario comentario) {
    return comentarioRepository.save(comentario);
  }

  @Override
  public Comentario update(Comentario comentario) {
    return comentarioRepository.save(comentario);
  }

  @Override
  public Comentario findCommentId(Integer commentId) {
    List<Comentario> comentarios = comentarioRepository.findByCommentId(commentId);
    Comentario comentarioData = new Comentario();
    for (Comentario comentario : comentarios) {
      comentarioData = comentario;
    }
    return comentarioData;
  }

  @Override
  public List<Comentario> findPublicacion(Publicacion publicacion) {
    return comentarioRepository.findByPublicacion(publicacion);
  }

  @Override
  public List<Comentario> findAll() {
    return comentarioRepository.findAll();
  }
}
