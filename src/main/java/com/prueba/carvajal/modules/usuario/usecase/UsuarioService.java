package com.prueba.carvajal.modules.usuario.usecase;

import com.prueba.carvajal.crosscutting.domain.dto.autentication.AuthModelResultMacro;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.LoginData;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.TokenData;
import com.prueba.carvajal.crosscutting.domain.dto.user.UserModelMacro;
import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import com.prueba.carvajal.crosscutting.utils.EncriptedSha512;
import com.prueba.carvajal.crosscutting.utils.TokenGenerator;
import com.prueba.carvajal.modules.usuario.dataproviders.IUsuarioDataProvider;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * UsuarioService
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@Log4j2
@Service
public class UsuarioService {

  @Autowired
  private IUsuarioDataProvider iUsuarioDataProvider;

  public Usuario getFindByUserId(String token) throws Exception {

    return iUsuarioDataProvider.findByUserId(
        Integer.valueOf(TokenGenerator.getIdUserFromToken(token))
    );
  }

  public Usuario findByCorreoElectronico(String correoElectronico) throws Exception {
    return iUsuarioDataProvider.findByCorreoElectronico(correoElectronico);
  }


  public AuthModelResultMacro login(LoginData loginData) throws NoSuchAlgorithmException {
    return Optional.ofNullable(iUsuarioDataProvider.findByCorreoElectronico(loginData.getEmail()))
        .map(usuario -> processUserAuthentication(usuario, loginData))
        .orElseGet(this::buildFailedAuthResult);
  }

  private AuthModelResultMacro processUserAuthentication(Usuario usuario, LoginData loginData) {
    if (validatePassword(usuario, loginData)) {
      return buildSuccessfulAuthResult(usuario);
    } else {
      return buildFailedAuthResult();
    }
  }

  private AuthModelResultMacro buildSuccessfulAuthResult(Usuario usuario) {

    String accessToken = TokenGenerator.createTokenMacro(TokenData.builder()
        .usuario(usuario)
        .expirationTime(TimeUnit.HOURS.toMillis(100))
        .build());

    UserModelMacro userInfo = UserModelMacro.builder()
        .userName(usuario.getNombre() + " " + usuario.getApellido())
        .email(usuario.getCorreoElectronico())
        .userId(usuario.getUserId())
        .build();

    return AuthModelResultMacro.builder()
        .accessToken(accessToken)
        .statusPassword(true)
        .userId(usuario.getUserId())
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


  private boolean validatePassword(Usuario usuario, LoginData loginData) {
    String passwordEncrypted = usuario.getContrasenaHash();
    String encryptPassword = null;
    try {
      encryptPassword = EncriptedSha512.encryptSHA512(
          TokenGenerator.decryptPassword(loginData.getPassword()));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return encryptPassword.equals(passwordEncrypted);
  }

}
