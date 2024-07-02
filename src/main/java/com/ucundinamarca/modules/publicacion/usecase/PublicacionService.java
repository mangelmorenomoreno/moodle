package com.ucundinamarca.modules.publicacion.usecase;

import com.ucundinamarca.crosscutting.domain.dto.publicacion.PublicacionDto;
import com.ucundinamarca.crosscutting.persistence.entity.Comentario;
import com.ucundinamarca.crosscutting.persistence.entity.Publicacion;
import com.ucundinamarca.crosscutting.utils.TokenGenerator;
import com.ucundinamarca.modules.comentario.dataproviders.IcomentarioDataProviders;
import com.ucundinamarca.modules.publicacion.dataproviders.IpublicacionDataProvider;
import com.ucundinamarca.modules.respuestacomentario.dataproviders.IrespuestaComentarioDataProvider;
import com.ucundinamarca.modules.usuario.dataproviders.IusuarioDataProvider;
import java.util.Date;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PublicacionService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@Log4j2
@Service
public class PublicacionService {

  @Autowired
  private IpublicacionDataProvider ipublicacionDataProvider;

  @Autowired
  private IusuarioDataProvider iusuarioDataProvider;
  @Autowired
  private IcomentarioDataProviders icomentarioDataProviders;

  @Autowired
  private IrespuestaComentarioDataProvider irespuestaComentarioDataProvider;

  /**
   * Guarda una nueva publicación en la base de datos.
   *
   * @param publicacionDto DTO de la publicación que contiene los datos del título y contenido.
   * @param token          Token JWT que se utiliza para identificar al usuario que crea
   *                       la publicación.
   * @return Boolean Verdadero la publicación se ha guardado correctamente, falso de lo contrario.
   * @throws Exception Si ocurre algún error durante el guardado de la publicación.
   */
  public Boolean save(PublicacionDto publicacionDto, String token) throws Exception {
    return ipublicacionDataProvider.save(Publicacion.builder()
                                           .titulo(publicacionDto.getTitulo())
                                           .fechaPublicacion(new Date())
                                           .usuario(iusuarioDataProvider.findByUserId(
                                             Integer.valueOf(
                                               TokenGenerator.getIdUserFromToken(token))))
                                           .contenido(publicacionDto.getContenido())
                                           .build()) != null;
  }

  public List<Publicacion> findAll() {
    return ipublicacionDataProvider.findAll();
  }

  /**
   * Actualiza una publicación existente en la base de datos con la información proporcionada.
   *
   * @param publicacionDto DTO de la publicación que contiene los datos a actualizar.
   * @param token          Token JWT que se utiliza para identificar al usuario que actualiza
   *                       la publicación.
   * @return Boolean Verdadero publicación se ha actualizado correctamente, falso de lo contrario.
   * @throws Exception Si ocurre algún error durante la actualización de la publicación.
   */
  public Boolean update(PublicacionDto publicacionDto, String token) throws Exception {
    return ipublicacionDataProvider.update(Publicacion.builder()
                                             .postId(publicacionDto.getPostId())
                                             .titulo(publicacionDto.getTitulo())
                                             .fechaPublicacion(new Date())
                                             .usuario(iusuarioDataProvider.findByUserId(
                                               Integer.valueOf(
                                                 TokenGenerator.getIdUserFromToken(token))))
                                             .contenido(publicacionDto.getContenido())
                                             .build()).getPostId() != null;
  }

  /**
   * Elimina una publicación y sus comentarios asociados de la base de datos.
   *
   * @param postId El identificador de la publicación a eliminar.
   * @return Boolean Verdadero si la publicación y sus comentarios asociados han sido eliminados
   */
  public Boolean delete(Integer postId) {

    List<Comentario> comentarios =
        icomentarioDataProviders.findPublicacion(ipublicacionDataProvider.findById(postId));
    for (Comentario comentario : comentarios) {
      irespuestaComentarioDataProvider.delete(
          irespuestaComentarioDataProvider.findByComentario(comentario));
    }
    icomentarioDataProviders.delete(icomentarioDataProviders.findPublicacion(
        ipublicacionDataProvider.findById(postId)));
    return ipublicacionDataProvider.delete(
      ipublicacionDataProvider.findById(postId)
    );
  }

  public List<Publicacion> findByUsuario(String token) throws Exception {
    return ipublicacionDataProvider.findByUsuario(iusuarioDataProvider.findByUserId(
      Integer.valueOf(TokenGenerator.getIdUserFromToken(token))));
  }

  public List<Publicacion> findByIdUser(Integer idUser) throws Exception {
    return ipublicacionDataProvider.findByUsuario(iusuarioDataProvider.findByUserId(idUser));
  }

  public Publicacion findById(Integer id) {
    return ipublicacionDataProvider.findById(id);
  }

}
