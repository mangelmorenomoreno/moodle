package com.prueba.carvajal.modules.credencial.usecase;

import com.prueba.carvajal.crosscutting.domain.dto.autentication.AuthModelResultMacro;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.CredencialData;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.LoginData;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.TokenData;
import com.prueba.carvajal.crosscutting.domain.dto.user.UserModelMacro;
import com.prueba.carvajal.crosscutting.persistence.entity.Credencial;

import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import com.prueba.carvajal.crosscutting.utils.EncriptedSha512;
import com.prueba.carvajal.crosscutting.utils.TokenGenerator;
import com.prueba.carvajal.modules.credencial.dataproviders.ICredencialDataProvider;
import com.prueba.carvajal.modules.sendmail.usecase.message.MessageSenderService;
import com.prueba.carvajal.modules.usuario.dataproviders.IUsuarioDataProvider;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CredencialService {

  @Autowired
  private ICredencialDataProvider iCredencialDataProvider;

  @Autowired
  private IUsuarioDataProvider iUsuarioDataProvider;

  @Autowired
  private MessageSenderService messageSenderService;

  @Value("${integration.queues.activate}")
  private String queuesActivate;

  @Value("${integration.queues.recuperarPassword}")
  private String recuperarPassword;


  public Boolean requestPasswordReset(String email) {
    Usuario usuario = iUsuarioDataProvider.findByCorreoElectronico(email);

    if (usuario != null) {
      Credencial credencial = iCredencialDataProvider.findByUserId(usuario.getUserId());
      credencial.setHashContraseña(TokenGenerator.createTokenMacroChangePassword(usuario));
      iCredencialDataProvider.save(credencial);
      messageSenderService.sendMessage(recuperarPassword, UserModelMacro.builder()
          .userId(usuario.getUserId())
          .email(usuario.getCorreoElectronico())
          .userName(usuario.getNombre() + " " + usuario.getApellido())
          .build());
      return true;
    } else {
      return false;
    }
  }


  public Boolean updatePassword(CredencialData credencialData, String token) throws Exception {
    Credencial credencial = iCredencialDataProvider.login(
        Integer.valueOf(TokenGenerator.getIdUserFromToken(token)));

    if (!validatePasswordNew(credencialData, credencial)) {
      return false;
    }

    updateCredencialWithNewPassword(credencial, credencialData.getPasswordNew());
    return true;
  }

  private void updateCredencialWithNewPassword(Credencial credencial, String newPassword) throws NoSuchAlgorithmException {
    credencial.setHashContraseña(EncriptedSha512.encryptSHA512(newPassword));
    credencial.setTokenResetPassword(null);
    iCredencialDataProvider.save(credencial);
  }


  public AuthModelResultMacro login(LoginData loginData) throws NoSuchAlgorithmException {

    return Optional.ofNullable(
            iCredencialDataProvider.login(
                iUsuarioDataProvider.findByCorreoElectronico(
                    loginData.getEmail()
                ).getUserId()
            )
        )
        .map(credencial -> processUserAuthentication(credencial, loginData))
        .orElseGet(this::buildFailedAuthResult);
  }

  private AuthModelResultMacro processUserAuthentication(Credencial credencial, LoginData loginData) {
    if (validatePassword(credencial, loginData)) {
      return buildSuccessfulAuthResult(credencial);
    } else {
      return buildFailedAuthResult();
    }
  }

  private AuthModelResultMacro buildSuccessfulAuthResult(Credencial credencial) {

    String accessToken = TokenGenerator.createTokenMacro(TokenData.builder()
        .usuario(credencial.getUsuario())
        .expirationTime(TimeUnit.HOURS.toMillis(100))
        .build());

    UserModelMacro userInfo = UserModelMacro.builder()
        .userName(credencial.getUsuario().getNombre() + " " + credencial.getUsuario().getApellido())
        .email(credencial.getUsuario().getCorreoElectronico())
        .userId(credencial.getUsuario().getUserId())
        .build();

    return AuthModelResultMacro.builder()
        .accessToken(accessToken)
        .statusPassword(true)
        .userId(credencial.getUsuario().getUserId())
        .userInfo(userInfo)
        .build();
  }

  private AuthModelResultMacro buildFailedAuthResult() {
    return AuthModelResultMacro.builder()
        .accessToken(null)
        .statusPassword(false)
        .userId(null)
        .userInfo(null)
        .build();
  }


  private boolean validatePassword(Credencial credencial, LoginData loginData) {
    String passwordEncrypted = credencial.getHashContraseña();
    String encryptPassword = null;
    try {
      encryptPassword = EncriptedSha512.encryptSHA512(
          TokenGenerator.decryptPassword(loginData.getPassword()));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return encryptPassword.equals(passwordEncrypted);
  }

  private boolean validatePasswordNew(CredencialData credencialData, Credencial credencial) {
    String passwordEncrypted = credencial.getHashContraseña();
    String encryptPassword = null;
    try {
      encryptPassword = EncriptedSha512.encryptSHA512(
          TokenGenerator.decryptPassword(credencialData.getPasswordOld()));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return encryptPassword.equals(passwordEncrypted);
  }

}
