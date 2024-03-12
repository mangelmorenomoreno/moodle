package com.prueba.carvajal.modules.comentario.api;


import com.prueba.carvajal.crosscutting.domain.constants.ApiDocumentationConstant;
import com.prueba.carvajal.crosscutting.domain.constants.ControllerConstants;
import com.prueba.carvajal.crosscutting.domain.constants.ResponseValueConstants;
import com.prueba.carvajal.crosscutting.domain.constants.SecurityConstants;
import com.prueba.carvajal.crosscutting.domain.dto.comentario.ComentarioDto;
import com.prueba.carvajal.crosscutting.domain.enums.MessageCodes;
import com.prueba.carvajal.crosscutting.patterns.IrestResponse;
import com.prueba.carvajal.crosscutting.persistence.entity.Comentario;
import com.prueba.carvajal.crosscutting.utils.ResponseEntityUtil;
import com.prueba.carvajal.modules.comentario.usecase.ComentarioService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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
 * ComentarioController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.COMENTARIO_URL)
public class ComentarioController {

  @Autowired
  private ComentarioService comentarioService;

  /**
   * Crea una nueva publicación de comentario.
   *
   * @param comentarioDto El DTO de Comentario que contiene los datos del comentario a crear.
   * @param token         El token JWT que se utiliza para la autenticación del usuario.
   * @return Una ResponseEntity que contiene una respuesta Irest con el resultado de la operación.
   * @throws Exception Si se produce algún error durante el proceso de creación.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.CREAR_COMENTARIO_VALUE,
      nickname = ApiDocumentationConstant.CREAR_COMENTARIO_NICK,
      notes = ApiDocumentationConstant.CREAR_COMENTARIO_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.CREAR_COMENTARIO_URL)
  public ResponseEntity<IrestResponse<Boolean>> crearPublicacion(
      @RequestBody ComentarioDto comentarioDto,
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      comentarioService.save(comentarioDto, token),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Lista todas las publicaciones de comentarios existentes.
   *
   * @param token El token JWT que se utiliza para la autenticación del usuario.
   * @return Una ResponseEntity que contiene una lista de comentarios en el cuerpo de la respuesta.
   * @throws Exception Si produce algún error durante el proceso de recuperación de los comentarios.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.LISTAR_COMENTARIO_VALUE,
      nickname = ApiDocumentationConstant.LISTAR_COMENTARIO_NICK,
      notes = ApiDocumentationConstant.LISTAR_COMENTARIO_NOTES,
      response = ResponseEntity.class)
  @GetMapping(ControllerConstants.LISTAR_COMENTARIO_URL)
  public ResponseEntity<IrestResponse<List<Comentario>>> listarPublicaciones(
      @RequestHeader(SecurityConstants.JWT_HEADER) String token
  ) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      comentarioService.findPublicacion(),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Elimina un comentario específico basado en su identificador.
   *
   * @param commentId El identificador del comentario a eliminar.
   * @param token     El token JWT que se utiliza para la autenticación del usuario.
   * @return Una ResponseEntity que contiene una respuesta Irest con el resultado de la operación.
   * @throws Exception Si se produce algún error durante el proceso de eliminación.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.ELIMINAR_COMENTARIO_VALUE,
      nickname = ApiDocumentationConstant.ELIMINAR_COMENTARIO_NICK,
      notes = ApiDocumentationConstant.ELIMINAR_COMENTARIO_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.ELIMINAR_COMENTARIO_URL)
  public ResponseEntity<IrestResponse<Boolean>> eliminarComentario(
      @RequestBody Integer commentId,
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      comentarioService.delete(commentId),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

}
