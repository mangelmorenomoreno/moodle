package com.ucundinamarca.modules.comentario.usecase;

import com.ucundinamarca.crosscutting.domain.dto.comentario.ComentarioDto;
import com.ucundinamarca.crosscutting.persistence.entity.Comentario;
import com.ucundinamarca.crosscutting.utils.TokenGenerator;
import com.ucundinamarca.modules.comentario.dataproviders.IcomentarioDataProviders;
import com.ucundinamarca.modules.publicacion.dataproviders.IpublicacionDataProvider;
import com.ucundinamarca.modules.usuario.dataproviders.IusuarioDataProvider;
import java.util.Date;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ComentarioService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Log4j2
@Service
public class ComentarioService {

  @Autowired
  private IcomentarioDataProviders icomentarioDataProviders;

  @Autowired
  private IpublicacionDataProvider ipublicacionDataProvider;

  @Autowired
  private IusuarioDataProvider iusuarioDataProvider;

  public Boolean delete(Integer commentId) {
    return icomentarioDataProviders.delete(icomentarioDataProviders.findCommentId(commentId));
  }

  /**
   * Guarda un comentario en la base de datos.
   * Este método convierte un ComentarioDto a un objeto Comentario, estableciendo
   * la fecha actual, el contenido del comentario, el usuario obtenido a través del ID
   * incluido en el token JWT y la publicación asociada al comentario.
   *
   * @param comentarioDto El Data Transfer Object (DTO) del comentario que contiene
   *                      la información necesaria para crear un comentario.
   * @param token El token JWT utilizado para identificar al usuario que realiza el comentario.
   * @return Boolean Verdadero si el comentario se ha guardado con contenido no nulo,
   *                 falso en caso contrario.
   * @throws Exception Si hay un error al obtener el usuario o la publicación desde
   *                   los proveedores de datos o al guardar el comentario.
   */
  public Boolean save(ComentarioDto comentarioDto, String token) throws Exception {


    return icomentarioDataProviders.save(
      Comentario.builder()
        .fechaComentario(new Date())
        .contenido(comentarioDto.getContenido())
        .usuario(iusuarioDataProvider.findByUserId(Integer.valueOf(
          TokenGenerator.getIdUserFromToken(token))))
        .publicacion(ipublicacionDataProvider.findById(comentarioDto.getPostId()))
        .build()).getContenido() != null;
  }


  public Comentario update(Comentario comentario) {
    return icomentarioDataProviders.save(comentario);
  }


  public Comentario findCommentId(Integer commentId) {
    return icomentarioDataProviders.findCommentId(commentId);
  }

  public List<Comentario> findPublicacion() {
    return icomentarioDataProviders.findAll();
  }
}
