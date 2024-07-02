package com.ucundinamarca.modules.usuario.api;

import com.ucundinamarca.crosscutting.domain.constants.ApiDocumentationConstant;
import com.ucundinamarca.crosscutting.domain.constants.ControllerConstants;
import com.ucundinamarca.crosscutting.domain.constants.ResponseValueConstants;
import com.ucundinamarca.crosscutting.domain.constants.SecurityConstants;
import com.ucundinamarca.crosscutting.domain.dto.user.BasicInformationUserDto;
import com.ucundinamarca.crosscutting.domain.enums.MessageCodes;
import com.ucundinamarca.crosscutting.patterns.IrestResponse;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import com.ucundinamarca.crosscutting.utils.ResponseEntityUtil;
import com.ucundinamarca.modules.sendmail.usecase.message.MessageSenderService;
import com.ucundinamarca.modules.usuario.usecase.UsuarioService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UsuarioController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */


@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.USUARIO_URL)
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private MessageSenderService messageSenderService;

  /**
   * Obtiene la información de un usuario basado en el token JWT proporcionado.
   * Anotado con @GetMapping para manejar solicitudes GET en la URL especificada. Retorna
   * los datos del usuario en un {@link ResponseEntity} con la respuesta encapsulada.
   *
   * @param token El token JWT del usuario cuya información se solicita.
   * @return Una {@link ResponseEntity} con los detalles del usuario.
   * @throws Exception Si hay un error en la recuperación de los datos del usuario.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.CONSULTAR_USUARIO_VALUE,
      nickname = ApiDocumentationConstant.CONSULTAR_USUARIO_NICK,
      notes = ApiDocumentationConstant.CONSULTAR_USUARIO_NOTES,
      response = ResponseEntity.class)
  @GetMapping(ControllerConstants.INFORMACION_USUARIO_URL)
  public ResponseEntity<IrestResponse<Usuario>> getUsuario(
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      usuarioService.getFindByUserId(token),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Busca un usuario por su correo electrónico.
   * Utiliza @GetMapping para responder a solicitudes en la URL correspondiente.
   * Retorna los detalles del usuario encontrados en un {@link ResponseEntity}.
   *
   * @param email El correo electrónico del usuario a buscar.
   * @return Una {@link ResponseEntity} con la información del usuario.
   * @throws Exception Si se produce un error en la búsqueda.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_VALUE,
      nickname = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_NICK,
      notes = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_NOTES,
      response = ResponseEntity.class)
  @GetMapping(ControllerConstants.INFORMACION_USUARIO_CORREO_URL)
  public ResponseEntity<IrestResponse<Usuario>> findByEmail(
      @PathVariable String email) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      usuarioService.findByCorreoElectronico(email),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Crea un nuevo usuario con la información básica proporcionada.
   * Anotado con @PostMapping, maneja solicitudes POST para crear un usuario.
   * Retorna un estado de éxito o fracaso en un {@link ResponseEntity}.
   *
   * @param basicInformationUserDto DTO con la información básica del usuario.
   * @return Una {@link ResponseEntity} indicando el éxito o fracaso de la creación.
   * @throws Exception Si se produce un error durante la creación.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.CREAR_USUARIO_VALUE,
      nickname = ApiDocumentationConstant.CREAR_USUARIO_NICK,
      notes = ApiDocumentationConstant.CREAR_USUARIO_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.CREAR_USUARIO_URL)
  public ResponseEntity<IrestResponse<Boolean>> crearUsuario(
      @RequestBody BasicInformationUserDto basicInformationUserDto) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      usuarioService.save(basicInformationUserDto),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Actualiza los datos de un usuario existente.
   * Usa @PostMapping para procesar solicitudes POST. Retorna el resultado de la actualización
   * en un {@link ResponseEntity}.
   *
   * @param basicInformationUserDto DTO con la información del usuario a actualizar.
   * @param token Token JWT para identificación del usuario.
   * @return Una {@link ResponseEntity} con el resultado de la actualización.
   * @throws Exception Si se produce un error durante la actualización.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.ACTUALIZAR_USUARIO_VALUE,
      nickname = ApiDocumentationConstant.ACTUALIZAR_USUARIO_NICK,
      notes = ApiDocumentationConstant.ACTUALIZAR_USUARIO_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.ACTUALIZAR_USUARIO_URL)
  public ResponseEntity<IrestResponse<Boolean>> actualizaUsuario(
      @RequestBody BasicInformationUserDto basicInformationUserDto,
      @RequestHeader(SecurityConstants.JWT_HEADER) String token) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      usuarioService.update(basicInformationUserDto, token),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }

  /**
   * Busca usuarios por correo electrónico o nombre.
   *
   * @param valor El valor de búsqueda para el nombre o correo electrónico.
   * @return Una {@link ResponseEntity} con una lista de usuarios coincidentes.
   * @throws Exception Si hay un error en la búsqueda.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_VALUE,
      nickname = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_NICK,
      notes = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_NOTES,
      response = ResponseEntity.class)
  @GetMapping(ControllerConstants.INFORMACION_USUARIO_O_CORREO_URL)
  public ResponseEntity<IrestResponse<List<Usuario>>> findByEmailOrNombre(
      @PathVariable String valor) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      usuarioService.findByNombreAndCorreoLike(valor),
      MessageCodes.PROCESS_SUCCES.getMessage(),
      MessageCodes.PROCESS_SUCCES.name());
  }


}
