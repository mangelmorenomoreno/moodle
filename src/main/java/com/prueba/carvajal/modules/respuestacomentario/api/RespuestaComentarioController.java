package com.prueba.carvajal.modules.respuestacomentario.api;

import com.prueba.carvajal.crosscutting.domain.constants.ApiDocumentationConstant;
import com.prueba.carvajal.crosscutting.domain.constants.ControllerConstants;
import com.prueba.carvajal.crosscutting.domain.constants.ResponseValueConstants;
import com.prueba.carvajal.crosscutting.domain.constants.SecurityConstants;
import com.prueba.carvajal.crosscutting.domain.dto.respuesta.RespuestaComentarioDto;
import com.prueba.carvajal.crosscutting.domain.enums.MessageCodes;
import com.prueba.carvajal.crosscutting.patterns.IrestResponse;
import com.prueba.carvajal.crosscutting.persistence.entity.RespuestaComentario;
import com.prueba.carvajal.crosscutting.utils.ResponseEntityUtil;
import com.prueba.carvajal.modules.respuestacomentario.usecase.RespuestaComentarioService;
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
 * RespuestaComentarioController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.RESPUESTA_COMENTARIO_URL)
public class RespuestaComentarioController {

  @Autowired
  private RespuestaComentarioService comentarioService;

  /**
   * Lista todas las respuestas a los comentarios existentes.
   *
   * @param token El token JWT para la autenticación del usuario.
   * @return Una ResponseEntity que encapsula una respuesta IRest con una lista RespuestaComentario.
   * @throws Exception Si ocurre un error durante la operación.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.LISTAR_COMENTARIO_VALUE,
      nickname = ApiDocumentationConstant.LISTAR_COMENTARIO_NICK,
      notes = ApiDocumentationConstant.LISTAR_COMENTARIO_NOTES,
      response = ResponseEntity.class)
  @GetMapping(ControllerConstants.LISTAR_RESPUESTA_URL)
  public ResponseEntity<IrestResponse<List<RespuestaComentario>>> listarPublicaciones(
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      comentarioService.findByAll(),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Guarda una respuesta a un comentario en la base de datos.
   *
   * @param respuestaComentarioDto DTO que contiene la información de la respuesta del comentario.
   * @param token El token JWT para la autenticación del usuario.
   * @return Una ResponseEntity que encapsula una respuesta IRest indicando el éxito la operación.
   * @throws Exception Si ocurre un error durante la operación de guardado.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.GUARDAR_COMENTARIO_VALUE,
      nickname = ApiDocumentationConstant.GUARDAR_COMENTARIO_NICK,
      notes = ApiDocumentationConstant.GUARDAR_COMENTARIO_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.GUARDAR_RESPUESTA_URL)
  public ResponseEntity<IrestResponse<Boolean>> guardarComentarios(
      @RequestBody RespuestaComentarioDto respuestaComentarioDto,
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      comentarioService.save(respuestaComentarioDto, token),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Elimina una respuesta a un comentario basado en su identificador.
   *
   * @param respuestaId El identificador de la respuesta del comentario a eliminar.
   * @param token El token JWT para la autenticación del usuario.
   * @return Una ResponseEntity que encapsula una respuesta IRest indicando el éxito de operación.
   * @throws Exception Si ocurre un error durante la operación de eliminación.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.ELIMINAR_PUBLICACION_VALUE,
      nickname = ApiDocumentationConstant.ELIMINAR_PUBLICACION_NICK,
      notes = ApiDocumentationConstant.ELIMINAR_PUBLICACION_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.ELIMINAR_PUBLICACION_URL)
  public ResponseEntity<IrestResponse<Boolean>> eliminarRespuestas(
      @RequestBody Integer respuestaId,
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      comentarioService.delete(respuestaId),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

}
