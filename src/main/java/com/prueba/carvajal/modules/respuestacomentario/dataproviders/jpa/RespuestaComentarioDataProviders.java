package com.prueba.carvajal.modules.respuestacomentario.dataproviders.jpa;

import com.prueba.carvajal.crosscutting.persistence.entity.Comentario;
import com.prueba.carvajal.crosscutting.persistence.entity.RespuestaComentario;
import com.prueba.carvajal.crosscutting.persistence.repository.RespuestaComentarioRepository;
import com.prueba.carvajal.modules.respuestacomentario.dataproviders.IrespuestaComentarioDataProvider;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RespuestaComentarioController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Log4j2
@Service
public class RespuestaComentarioDataProviders implements IrespuestaComentarioDataProvider {

  @Autowired
  private RespuestaComentarioRepository comentarioRepository;

  @Override
  public RespuestaComentario save(RespuestaComentario comentario) {
    return comentarioRepository.save(comentario);
  }

  @Override
  public RespuestaComentario update(RespuestaComentario comentario) {
    return comentarioRepository.save(comentario);
  }

  @Override
  public Boolean delete(List<RespuestaComentario> respuestaComentario) {
    for (RespuestaComentario respuestaComentario1 : respuestaComentario) {

      comentarioRepository.delete(respuestaComentario1);
    }
    return true;
  }

  @Override
  public List<RespuestaComentario> findById(Integer respuestaId) {
    return comentarioRepository.findById(respuestaId).stream().toList();
  }

  @Override
  public List<RespuestaComentario> findByAll() {
    return comentarioRepository.findAll();
  }

  @Override
  public List<RespuestaComentario> findByComentario(Comentario comentario) {
    return comentarioRepository.findByComentario(comentario);
  }
}
