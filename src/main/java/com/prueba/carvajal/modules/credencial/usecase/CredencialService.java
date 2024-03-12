package com.prueba.carvajal.modules.credencial.usecase;

import com.prueba.carvajal.crosscutting.domain.dto.autentication.AuthModelResultMacro;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.CredencialData;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.LoginData;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.RegistrarCredencialDto;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.TokenData;
import com.prueba.carvajal.crosscutting.domain.dto.user.UserModelMacro;
import com.prueba.carvajal.crosscutting.persistence.entity.Credencial;
import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import com.prueba.carvajal.crosscutting.utils.EncriptedSha512;
import com.prueba.carvajal.crosscutting.utils.TokenGenerator;
import com.prueba.carvajal.modules.credencial.dataproviders.IcredencialDataProvider;
import com.prueba.carvajal.modules.sendmail.usecase.message.MessageSenderService;
import com.prueba.carvajal.modules.usuario.dataproviders.IusuarioDataProvider;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * CredencialService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Log4j2
@Service
public class CredencialService {

  @Autowired
  private IcredencialDataProvider icredencialDataProvider;

  @Autowired
  private IusuarioDataProvider iusuarioDataProvider;

  @Autowired
  private MessageSenderService messageSenderService;

  @Value("${integration.queues.activate}")
  private String queuesActivate;

  @Value("${integration.queues.recuperarPassword}")
  private String recuperarPassword;

  /**
   * Solicita el restablecimiento de la contraseña para un usuario basado en su correo electrónico.
   *
   * @param email El correo electrónico del usuario que solicita el restablecimiento.
   * @return Un Boolean que indica si el proceso de solicitud de restablecimiento de contraseña
   *         se inició con éxito (true) o no (false).
   */
  public Boolean requestPasswordReset(String email) {
    Usuario usuario = iusuarioDataProvider.findByCorreoElectronico(email);

    if (usuario != null) {
      Credencial credencial = icredencialDataProvider.findByUserId(usuario.getUserId());
      credencial.setTokenResetPassword(TokenGenerator.createTokenMacroChangePassword(usuario));
      icredencialDataProvider.save(credencial);
      messageSenderService.sendMessage(recuperarPassword, UserModelMacro.builder()
                             .userId(usuario.getUserId())
                             .email(usuario.getCorreoElectronico())
                             .userName(usuario.getNombre() + " "
                                         + usuario.getApellido())
                             .build());
      return true;
    } else {
      return false;
    }
  }

  /**
   * Actualiza la contraseña de un usuario validando el token proporcionado.
   * Este método recupera la credencial del usuario a través de un token JWT y, si la validación es
   * exitosa, actualiza la contraseña con la nueva proporcionada. Retorna true si la actualización
   * es exitosa o false si la validación falla.
   *
   * @param credencialData Los datos que contienen la nueva contraseña.
   * @param token El token JWT utilizado para validar la solicitud.
   * @return Boolean indicando si la actualización de la contraseña fue exitosa (true) o no (false).
   * @throws Exception Si ocurre un error durante la actualización de la contraseña.
   */
  public Boolean updatePassword(CredencialData credencialData, String token) throws Exception {
    Credencial credencial = icredencialDataProvider.login(
        Integer.valueOf(TokenGenerator.getIdUserFromToken(token)));

    if (!validatePasswordNew(credencialData, credencial)) {
      return false;
    }

    updateCredencialWithNewPassword(credencial, credencialData.getPasswordNew());
    return true;
  }

  /**
   * Crea una nueva contraseña para un usuario basándose en un token de restablecimiento.
   * Este método valida el token de restablecimiento proporcionado y, si es válido,
   * crea una nueva contraseña
   * para el usuario. Retorna true si la creación es exitosa y false si la validación
   * del token falla
   * o si la contraseña anterior no es válida.
   *
   * @param credencialData El DTO que contiene la nueva contraseña a crear.
   * @param token El token de restablecimiento proporcionado para la validación.
   * @return Boolean indicando si la creación de la nueva contraseña fue exitosa  no .
   * @throws Exception Si ocurre un error durante el proceso de creación de la nueva contraseña.
   */
  public Boolean createPassword(RegistrarCredencialDto credencialData, String token)
      throws Exception {
    try {
      Integer userId = Integer.valueOf(TokenGenerator.getIdUserFromToken(token));
      Credencial credencial = icredencialDataProvider.login(userId);
      if (credencial.getTokenResetPassword() == null
            || !credencial.getTokenResetPassword().equals(token)) {
        return false;
      }
      if (validatePasswordOld(credencialData, credencial)) {
        return false;
      }
      updateCredencialWithNewPassword(credencial, credencialData.getPassword());
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private void updateCredencialWithNewPassword(Credencial credencial, String newPassword)
      throws NoSuchAlgorithmException {
    credencial.setHashContrasena(EncriptedSha512.encryptSha512(
        TokenGenerator.decryptPassword(newPassword)));
    credencial.setTokenResetPassword(null);
    icredencialDataProvider.save(credencial);
  }

  /**
   * Autentica a un usuario basándose en los datos de inicio de sesión proporcionados.
   * Este método busca la credencial del usuario utilizando su correo electrónico y,
   * si lo encuentra,
   * procede con la autenticación. Si la autenticación es exitosa, retorna un objeto
   * {@link AuthModelResultMacro}
   * con los detalles del usuario autenticado. En caso de cualquier error o si la autenticación
   * falla,
   * retorna un resultado de autenticación fallida.
   *
   * @param loginData Los datos de inicio de sesión que incluyen el correo electrónico
   *                  y la contraseña.
   * @return Un objeto {@link AuthModelResultMacro} con el resultado de la autenticación.
   * @throws NoSuchAlgorithmException Si ocurre un error durante el proceso de autenticación.
   */
  public AuthModelResultMacro login(LoginData loginData) throws NoSuchAlgorithmException {
    try {
      return Optional.ofNullable(
          icredencialDataProvider.login(
            iusuarioDataProvider.findByCorreoElectronico(
              loginData.getEmail()
            ).getUserId()
          )
        )
               .map(credencial -> processUserAuthentication(credencial, loginData))
               .orElseGet(this::buildFailedAuthResult);
    } catch (Exception e) {
      return buildFailedAuthResult();
    }

  }

  private AuthModelResultMacro processUserAuthentication(
      Credencial credencial, LoginData loginData) {
    if (validatePassword(credencial, loginData)) {
      return buildSuccessfulAuthResult(credencial);
    } else {
      return buildFailedAuthResult();
    }
  }

  private AuthModelResultMacro buildSuccessfulAuthResult(Credencial credencial) {

    String accessToken = TokenGenerator.createTokenMacro(TokenData.builder()
                                                           .usuario(credencial.getUsuario())
                                                           .expirationTime(
                                                             TimeUnit.HOURS.toMillis(100))
                                                           .build());

    UserModelMacro userInfo = UserModelMacro.builder()
                                .userName(
                                  credencial.getUsuario().getNombre() + " "
                                    + credencial.getUsuario().getApellido())
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
    String passwordEncrypted = credencial.getHashContrasena();
    String encryptPassword = null;
    try {
      encryptPassword = EncriptedSha512.encryptSha512(
        TokenGenerator.decryptPassword(loginData.getPassword()));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return encryptPassword.equals(passwordEncrypted);
  }

  private boolean validatePasswordNew(CredencialData credencialData, Credencial credencial) {
    String passwordEncrypted = credencial.getHashContrasena();
    String encryptPassword = null;
    try {
      encryptPassword = EncriptedSha512.encryptSha512(
        TokenGenerator.decryptPassword(credencialData.getPasswordOld()));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return encryptPassword.equals(passwordEncrypted);
  }

  private boolean validatePasswordOld(RegistrarCredencialDto credencialData,
                                      Credencial credencial) {
    String passwordEncrypted = credencial.getHashContrasena();
    String encryptPassword = null;
    try {
      encryptPassword = EncriptedSha512.encryptSha512(
        TokenGenerator.decryptPassword(credencialData.getPassword()));
      log.error(encryptPassword);
      log.error(passwordEncrypted);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return encryptPassword.equals(passwordEncrypted);
  }

}
