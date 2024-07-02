package com.ucundinamarca.modules.publicacion.api;

import com.ucundinamarca.crosscutting.domain.constants.ApiDocumentationConstant;
import com.ucundinamarca.crosscutting.domain.constants.ControllerConstants;
import com.ucundinamarca.crosscutting.domain.constants.ResponseValueConstants;
import com.ucundinamarca.crosscutting.domain.constants.SecurityConstants;
import com.ucundinamarca.crosscutting.domain.dto.publicacion.PublicacionDto;
import com.ucundinamarca.crosscutting.domain.enums.MessageCodes;
import com.ucundinamarca.crosscutting.patterns.IrestResponse;
import com.ucundinamarca.crosscutting.persistence.entity.Publicacion;
import com.ucundinamarca.crosscutting.utils.ResponseEntityUtil;
import com.ucundinamarca.modules.publicacion.usecase.PublicacionService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PublicacionController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.PUBLICACION_URL)
public class PublicacionController {

  @Autowired
  private PublicacionService publicacionService;

  /**
   * Crea una nueva publicación en el sistema.
   *
   * @param publicacionDto El DTO de Publicación contiene la información para la nueva publicación.
   * @param token El token JWT para la autenticación del usuario.
   * @return Una ResponseEntity con el resultado de la operación y un mensaje de éxito.
   * @throws Exception Si ocurre un error durante la creación de la publicación.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.CREAR_PUBLICACION_VALUE,
      nickname = ApiDocumentationConstant.CREAR_PUBLICACION_NICK,
      notes = ApiDocumentationConstant.CREAR_PUBLICACION_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.CREAR_PUBLICACION_URL)
  public ResponseEntity<IrestResponse<Boolean>> crearPublicacion(
      @RequestBody PublicacionDto publicacionDto,
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      publicacionService.save(publicacionDto, token),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Obtiene y lista todas las publicaciones existentes en el sistema.
   *
   * @param token El token JWT para la autenticación del usuario.
   * @return Una ResponseEntity con una lista de todas las publicaciones y un mensaje de éxito.
   * @throws Exception Si ocurre un error al recuperar la lista de publicaciones.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.LISTAR_PUBLICACION_VALUE,
      nickname = ApiDocumentationConstant.LISTAR_PUBLICACION_NICK,
      notes = ApiDocumentationConstant.LISTAR_PUBLICACION_NOTES,
      response = ResponseEntity.class)
  @GetMapping(ControllerConstants.LISTAR_PUBLICACION_URL)
  public ResponseEntity<IrestResponse<List<Publicacion>>> listarPublicaciones(
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      publicacionService.findAll(),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Elimina una publicación existente en el sistema.
   *
   * @param postId El ID de la publicación a eliminar.
   * @param token El token JWT para la autenticación del usuario.
   * @return Una ResponseEntity con el resultado de la operación y un mensaje de éxito.
   * @throws Exception Si ocurre un error durante la eliminación de la publicación.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.ELIMINAR_PUBLICACION_VALUE,
      nickname = ApiDocumentationConstant.ELIMINAR_PUBLICACION_NICK,
      notes = ApiDocumentationConstant.ELIMINAR_PUBLICACION_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.ELIMINAR_PUBLICACION_URL)
  public ResponseEntity<IrestResponse<Boolean>> eliminarPublicacion(
      @RequestBody Integer postId,
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      publicacionService.delete(postId),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Actualiza la información de una publicación existente.
   *
   * @param publicacionDto El DTO de Publicación que contiene la información actualizada.
   * @param token El token JWT para la autenticación del usuario.
   * @return Una ResponseEntity con el resultado de la operación y un mensaje de éxito.
   * @throws Exception Si ocurre un error durante la actualización de la publicación.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.ACTUALIZAR_PUBLICACION_VALUE,
      nickname = ApiDocumentationConstant.ACTUALIZAR_PUBLICACION_NICK,
      notes = ApiDocumentationConstant.ACTUALIZAR_PUBLICACION_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.ACTUALIZAR_PUBLICACION_URL)
  public ResponseEntity<IrestResponse<Boolean>> actualizarPublicacion(
      @RequestBody PublicacionDto publicacionDto,
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      publicacionService.update(publicacionDto, token),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }


}
