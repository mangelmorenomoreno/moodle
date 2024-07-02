package com.ucundinamarca.modules.respuestacomentario.usecase;

import com.ucundinamarca.crosscutting.domain.dto.respuesta.RespuestaComentarioDto;
import com.ucundinamarca.crosscutting.persistence.entity.RespuestaComentario;
import com.ucundinamarca.crosscutting.utils.TokenGenerator;
import com.ucundinamarca.modules.comentario.dataproviders.IcomentarioDataProviders;
import com.ucundinamarca.modules.respuestacomentario.dataproviders.IrespuestaComentarioDataProvider;
import com.ucundinamarca.modules.usuario.dataproviders.IusuarioDataProvider;
import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RespuestaComentarioService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Log4j2
@Service
public class RespuestaComentarioService {

  @Autowired
  private IusuarioDataProvider iusuarioDataProvider;

  @Autowired
  private IrespuestaComentarioDataProvider irespuestaComentarioDataProvider;
  @Autowired
  private IcomentarioDataProviders icomentarioDataProviders;

  /**
   * Guarda una nueva respuesta de comentario en la base de datos.
   * Este método crea un objeto RespuestaComentario a partir de un DTO y el token JWT proporcionado.
   * Establece el usuario (basado en el token), el comentario al que se responde, el contenido de
   * la respuesta, y la fecha actual como fecha de respuesta.
   * Luego, guarda esta respuesta de comentario en la base de datos.
   *
   * @param respuestaComentarioDto DTO que contiene la información necesaria para crear
   *                               una respuesta a un comentario.
   * @param token El token JWT que se utiliza para identificar al usuario que crea la respuesta.
   * @return Boolean Verdadero si la respuesta al comentario se ha guardado correctamente.
   * @throws Exception Si ocurre algún error durante el proceso de guardado.
   */
  public Boolean save(RespuestaComentarioDto respuestaComentarioDto,
                      String token) throws Exception {
    irespuestaComentarioDataProvider.save(RespuestaComentario.builder()
                                                   .usuario(iusuarioDataProvider.findByUserId(
                                                     Integer.valueOf(
                                                       TokenGenerator.getIdUserFromToken(token))))
                                                   .comentario(
                                                     icomentarioDataProviders.findCommentId(
                                                       respuestaComentarioDto.getCommentId()))
                                                   .contenido(respuestaComentarioDto.getContenido())
                                                   .fechaRespuesta(LocalDateTime.now())
                                                   .contenido(respuestaComentarioDto.getContenido())
                                                   .build());
    return true;
  }

  public RespuestaComentario update(RespuestaComentario comentario) {
    return irespuestaComentarioDataProvider.update(comentario);
  }

  public Boolean delete(Integer  respuestaId) {
    irespuestaComentarioDataProvider.delete(irespuestaComentarioDataProvider.findById(respuestaId));
    return true;
  }

  public List<RespuestaComentario> findById(Integer respuestaId) {
    return irespuestaComentarioDataProvider.findById(respuestaId);
  }

  public List<RespuestaComentario> findByAll() {
    return irespuestaComentarioDataProvider.findByAll();
  }


}
