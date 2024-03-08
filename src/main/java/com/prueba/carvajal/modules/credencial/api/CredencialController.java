package com.prueba.carvajal.modules.credencial.api;


import com.prueba.carvajal.crosscutting.domain.constants.ApiDocumentationConstant;
import com.prueba.carvajal.crosscutting.domain.constants.ControllerConstants;
import com.prueba.carvajal.crosscutting.domain.constants.ResponseValueConstants;
import com.prueba.carvajal.crosscutting.domain.constants.SecurityConstants;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.AuthModelResultMacro;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.CredencialData;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.LoginData;
import com.prueba.carvajal.crosscutting.domain.enums.MessageCodes;
import com.prueba.carvajal.crosscutting.patterns.IRestResponse;
import com.prueba.carvajal.crosscutting.utils.ResponseEntityUtil;
import com.prueba.carvajal.modules.credencial.usecase.CredencialService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.CREDENCIAL_URL)
public class CredencialController {

  @Autowired
  private CredencialService credencialService;


  @ApiOperation(
      value = ApiDocumentationConstant.LOGIN_VALUE,
      nickname = ApiDocumentationConstant.LOGIN_NICK,
      notes = ApiDocumentationConstant.LOGIN_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.LOGIN)
  public ResponseEntity<IRestResponse<AuthModelResultMacro>> login(
      @RequestBody LoginData loginData)
      throws Exception {
    return ResponseEntityUtil.createSuccessfulResponse(
        ResponseValueConstants.SUCCESS,
        credencialService.login(loginData),
        MessageCodes.TRUE.getMessage(),
        MessageCodes.TRUE.name());
  }

  @ApiOperation(
      value = ApiDocumentationConstant.CREDENCIAL_UPDATE_VALUE,
      nickname = ApiDocumentationConstant.CREDENCIAL_UPDATE_NICK,
      notes = ApiDocumentationConstant.CREDENCIAL_UPDATE_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.ACTUALIZAR_CREDENCIAL_URL)
  public ResponseEntity<IRestResponse<Boolean>> resetPassword(
      @RequestHeader(SecurityConstants.JWT_HEADER) String token,
      @RequestBody CredencialData credencialData) throws Exception {

    return ResponseEntityUtil.createSuccessfulResponse(
        ResponseValueConstants.SUCCESS,
        credencialService.updatePassword(credencialData, token),
        MessageCodes.TRUE.getMessage(),
        MessageCodes.TRUE.name());
  }

  @ApiOperation(
      value = ApiDocumentationConstant.RESET_CREDENCIAL_VALUE,
      nickname = ApiDocumentationConstant.RESET_CREDENCIAL_NICK,
      notes = ApiDocumentationConstant.RESET_CREDENCIAL_NOTES,
      response = ResponseEntity.class)
  @PostMapping(ControllerConstants.RESTABLECER_PASSWORD_URL)
  public ResponseEntity<IRestResponse<Boolean>> recuperarPassword(@RequestParam String email) {

    return ResponseEntityUtil.createSuccessfulResponse(
        ResponseValueConstants.SUCCESS,
        credencialService.requestPasswordReset(email),
        MessageCodes.TRUE.getMessage(),
        MessageCodes.TRUE.name());
  }


}
