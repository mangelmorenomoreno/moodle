package com.prueba.carvajal.modules.credencial.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.prueba.carvajal.crosscutting.domain.dto.autentication.AuthModelResultMacro;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.CredencialData;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.LoginData;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.RegistrarCredencialDto;
import com.prueba.carvajal.crosscutting.patterns.IrestResponse;
import com.prueba.carvajal.modules.credencial.usecase.CredencialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * CredencialControllerTest.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
public class CredencialControllerTest {

  @InjectMocks
  private CredencialController credencialController;

  @Mock
  private CredencialService credencialService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void loginTest() throws Exception {
    LoginData loginData = new LoginData();
    AuthModelResultMacro authResult = AuthModelResultMacro.builder().build();
    when(credencialService.login(loginData)).thenReturn(authResult);

    ResponseEntity<IrestResponse<AuthModelResultMacro>> response =
        credencialController.login(loginData);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(authResult, response.getBody().getData());
  }

  @Test
  public void resetPasswordTest() throws Exception {
    String token = "dummyToken";
    CredencialData credencialData = new CredencialData();
    when(credencialService.updatePassword(credencialData, token)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response =
        credencialController.resetPassword(token, credencialData);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  public void registrarPasswordTest() throws Exception {
    String token = "dummyToken";
    RegistrarCredencialDto credencialDto = new RegistrarCredencialDto();
    when(credencialService.createPassword(credencialDto, token)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response =
        credencialController.registrarPassword(token, credencialDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  public void recuperarPasswordTest() {
    String email = "test@example.com";
    when(credencialService.requestPasswordReset(email)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response =
        credencialController.recuperarPassword(email);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }


}