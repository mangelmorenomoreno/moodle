package com.prueba.carvajal.modules.usuario.api;

import com.prueba.carvajal.crosscutting.domain.constants.ApiDocumentationConstant;
import com.prueba.carvajal.crosscutting.domain.constants.ControllerConstants;
import com.prueba.carvajal.crosscutting.domain.constants.ResponseValueConstants;
import com.prueba.carvajal.crosscutting.domain.constants.SecurityConstants;
import com.prueba.carvajal.crosscutting.domain.dto.user.BasicInformationUserDTO;
import com.prueba.carvajal.crosscutting.domain.enums.MessageCodes;
import com.prueba.carvajal.crosscutting.patterns.IRestResponse;
import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import com.prueba.carvajal.crosscutting.utils.ResponseEntityUtil;
import com.prueba.carvajal.modules.sendmail.usecase.message.MessageSenderService;
import com.prueba.carvajal.modules.usuario.usecase.UsuarioService;
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
 * UsuarioController
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


  @ApiOperation(
      value = ApiDocumentationConstant.CONSULTAR_USUARIO_VALUE,
      nickname = ApiDocumentationConstant.CONSULTAR_USUARIO_NICK,
      notes = ApiDocumentationConstant.CONSULTAR_USUARIO_NOTES,
      response = ResponseEntity.class)
  @GetMapping(ControllerConstants.INFORMACION_USUARIO_URL)
  public ResponseEntity<IRestResponse<Usuario>> getUsuario(
      @RequestHeader(SecurityConstants.JWT_HEADER) String token)
      throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
        ResponseValueConstants.SUCCESS,
        usuarioService.getFindByUserId(token),
        MessageCodes.GET_USER_PROFILE_001.getMessage(),
        MessageCodes.GET_USER_PROFILE_001.name());
  }


  @ApiOperation(
      value = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_VALUE,
      nickname = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_NICK,
      notes = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_NOTES,
      response = ResponseEntity.class)
  @GetMapping(ControllerConstants.INFORMACION_USUARIO_CORREO_URL)
  public ResponseEntity<IRestResponse<Usuario>> findByEmail(
      @PathVariable String email)
      throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
        ResponseValueConstants.SUCCESS,
        usuarioService.findByCorreoElectronico(email),
        MessageCodes.GET_USER_PROFILE_001.getMessage(),
        MessageCodes.GET_USER_PROFILE_001.name());
  }


  @ApiOperation(
      value = ApiDocumentationConstant.CREAR_USUARIO_VALUE,
      nickname = ApiDocumentationConstant.CREAR_USUARIO_NICK,
      notes = ApiDocumentationConstant.CREAR_USUARIO_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.CREAR_USUARIO_URL)
  public ResponseEntity<IRestResponse<Boolean>> crearUsuario(
      @RequestBody BasicInformationUserDTO basicInformationUserDTO)
      throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
        ResponseValueConstants.SUCCESS,
        usuarioService.save(basicInformationUserDTO),
        MessageCodes.GET_USER_PROFILE_001.getMessage(),
        MessageCodes.GET_USER_PROFILE_001.name());
  }

  @ApiOperation(
      value = ApiDocumentationConstant.ACTUALIZAR_USUARIO_VALUE,
      nickname = ApiDocumentationConstant.ACTUALIZAR_USUARIO_NICK,
      notes = ApiDocumentationConstant.ACTUALIZAR_USUARIO_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.ACTUALIZAR_USUARIO_URL)
  public ResponseEntity<IRestResponse<Boolean>> actualizaUsuario(
      @RequestBody BasicInformationUserDTO basicInformationUserDTO,
      @RequestHeader(SecurityConstants.JWT_HEADER) String token)
      throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
        ResponseValueConstants.SUCCESS,
        usuarioService.update(basicInformationUserDTO, token),
        MessageCodes.GET_USER_PROFILE_001.getMessage(),
        MessageCodes.GET_USER_PROFILE_001.name());
  }

  @ApiOperation(
      value = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_VALUE,
      nickname = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_NICK,
      notes = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_NOTES,
      response = ResponseEntity.class)
  @GetMapping(ControllerConstants.INFORMACION_USUARIO_O_CORREO_URL)
  public ResponseEntity<IRestResponse<List<Usuario>>> findByEmailOrNombre(
      @PathVariable String valor)
      throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
        ResponseValueConstants.SUCCESS,
        usuarioService.findByNombreAndCorreoLike(valor),
        MessageCodes.GET_USER_PROFILE_001.getMessage(),
        MessageCodes.GET_USER_PROFILE_001.name());
  }


}
