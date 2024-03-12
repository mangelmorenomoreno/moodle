package com.prueba.carvajal.modules.credencial.api;


import com.prueba.carvajal.crosscutting.domain.constants.ApiDocumentationConstant;
import com.prueba.carvajal.crosscutting.domain.constants.ControllerConstants;
import com.prueba.carvajal.crosscutting.domain.constants.ResponseValueConstants;
import com.prueba.carvajal.crosscutting.domain.constants.SecurityConstants;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.AuthModelResultMacro;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.CredencialData;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.LoginData;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.RegistrarCredencialDto;
import com.prueba.carvajal.crosscutting.domain.enums.MessageCodes;
import com.prueba.carvajal.crosscutting.patterns.IrestResponse;
import com.prueba.carvajal.crosscutting.utils.ResponseEntityUtil;
import com.prueba.carvajal.modules.credencial.usecase.CredencialService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * CredencialController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.CREDENCIAL_URL)
public class CredencialController {

  @Autowired
  private CredencialService credencialService;

  /**
   * Controlador para manejar las solicitudes de inicio de sesión.
   * Este método, anotado con @PostMapping, se encarga de procesar las peticiones POST a
   * la ruta especificada
   * para el inicio de sesión. Utiliza {@link ApiOperation} para la documentación de Swagger,
   * especificando
   * detalles como el valor, apodo y notas de la API.
   * Al recibir datos de inicio de sesión ({@link LoginData}), los procesa a través del servicio
   * de credenciales
   * y retorna una respuesta encapsulada en un {@link ResponseEntity}. Esta respuesta incluye el
   * resultado de la
   * autenticación y mensajes asociados.
   *
   * @param loginData Los datos de inicio de sesión del usuario que incluyen, por ejemplo,
   *                  el nombre de usuario
   *                  y la contraseña.
   * @return Una {@link ResponseEntity} que contiene la respuesta de autenticación
   */
  @ApiOperation(
      value = ApiDocumentationConstant.LOGIN_VALUE,
      nickname = ApiDocumentationConstant.LOGIN_NICK,
      notes = ApiDocumentationConstant.LOGIN_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.LOGIN)
  public ResponseEntity<IrestResponse<AuthModelResultMacro>>
      login(@RequestBody LoginData loginData) throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      credencialService.login(loginData),
      MessageCodes.TRUE.getMessage(),
      MessageCodes.TRUE.name());
  }

  /**
   * Controlador para el cambio de contraseña de un usuario.
   * Este método, anotado con @PostMapping, maneja las solicitudes POST para actualizar
   * la contraseña de un usuario.
   * Se documenta usando @ApiOperation de Swagger, indicando los detalles de la operación.
   * Recibe un token JWT en el encabezado y los datos de la nueva credencial en el cuerpo de
   * la solicitud.
   * Retorna una respuesta estándar de tipo {@link ResponseEntity} que indica si la operación
   * fue exitosa o no.
   *
   * @param token El token JWT proporcionado en el encabezado de la solicitud.
   * @param credencialData Los datos de la nueva credencial del usuario.
   * @return Una {@link ResponseEntity} con una respuesta estándar indicando el éxito o fracaso.
   * @throws Exception Si ocurre un error durante la actualización de la contraseña.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.CREDENCIAL_UPDATE_VALUE,
      nickname = ApiDocumentationConstant.CREDENCIAL_UPDATE_NICK,
      notes = ApiDocumentationConstant.CREDENCIAL_UPDATE_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.ACTUALIZAR_CREDENCIAL_URL)
  public ResponseEntity<IrestResponse<Boolean>> resetPassword(
      @RequestHeader(SecurityConstants.JWT_HEADER) String token,
      @RequestBody CredencialData credencialData) throws Exception {

    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      credencialService.updatePassword(credencialData, token),
      MessageCodes.TRUE.getMessage(),
      MessageCodes.TRUE.name());
  }

  /**
   * Controlador para registrar una nueva contraseña.
   * Este método gestiona las peticiones POST para crear una nueva contraseña. Utiliza
   * documentar la operación en Swagger.
   *
   * @param token El token JWT proporcionado en el encabezado de la solicitud.
   * @param credencialDto El DTO que contiene la información necesaria para registrar
   *                      la nueva contraseña.
   * @return Una {@link ResponseEntity} que indica si la creación de la nueva contraseña.
   * @throws Exception Si ocurre un error durante el registro de la nueva contraseña.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.CREDENCIAL_CREATE_VALUE,
      nickname = ApiDocumentationConstant.CREDENCIAL_CREATE_NICK,
      notes = ApiDocumentationConstant.CREDENCIAL_CREATE_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.REGISTRAR_CREDENCIAL_URL)
  public ResponseEntity<IrestResponse<Boolean>> registrarPassword(
      @RequestHeader(SecurityConstants.JWT_HEADER) String token,
      @RequestBody RegistrarCredencialDto credencialDto) throws Exception {

    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      credencialService.createPassword(credencialDto, token),
      MessageCodes.TRUE.getMessage(),
      MessageCodes.TRUE.name());
  }

  /**
   * Controlador para gestionar la solicitud de restablecimiento de contraseña.
   * Este método maneja las peticiones POST para iniciar el proceso de restablecimiento
   * de contraseña.
   * Se documenta a través de @ApiOperation para su descripción en Swagger.
   * Recibe el correo electrónico del usuario en la ruta de la URL y procede a solicitar
   * un restablecimiento
   * de contraseña. Retorna una respuesta encapsulada en un {@link ResponseEntity}.
   *
   * @param email El correo electrónico del usuario que solicita el restablecimiento
   *              de contraseña.
   * @return Una {@link ResponseEntity} que indica si la solicitud de restablecimiento.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.RESET_CREDENCIAL_VALUE,
      nickname = ApiDocumentationConstant.RESET_CREDENCIAL_NICK,
      notes = ApiDocumentationConstant.RESET_CREDENCIAL_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.RESTABLECER_PASSWORD_URL)
  public ResponseEntity<IrestResponse<Boolean>> recuperarPassword(@PathVariable String email) {

    return ResponseEntityUtil.createSuccessfulResponse(
      ResponseValueConstants.SUCCESS,
      credencialService.requestPasswordReset(email),
      MessageCodes.TRUE.getMessage(),
      MessageCodes.TRUE.name());
  }


}
