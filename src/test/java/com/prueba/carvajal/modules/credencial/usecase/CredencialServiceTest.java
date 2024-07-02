package com.prueba.carvajal.modules.credencial.usecase;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ucundinamarca.crosscutting.domain.dto.autentication.CredencialData;
import com.ucundinamarca.crosscutting.domain.dto.autentication.RegistrarCredencialDto;
import com.ucundinamarca.crosscutting.domain.dto.user.UserModelMacro;
import com.ucundinamarca.crosscutting.persistence.entity.Credencial;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import com.ucundinamarca.modules.credencial.dataproviders.IcredencialDataProvider;
import com.ucundinamarca.modules.credencial.usecase.CredencialService;
import com.ucundinamarca.modules.sendmail.usecase.message.MessageSenderService;
import com.ucundinamarca.modules.usuario.dataproviders.IusuarioDataProvider;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


/**
 * UsuarioDataProviderTest.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
public class CredencialServiceTest {

  @InjectMocks
  private CredencialService credencialService;

  @Mock
  private IcredencialDataProvider icredencialDataProvider;

  @Mock
  private IusuarioDataProvider iusuarioDataProvider;

  @Mock
  private MessageSenderService messageSenderService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void requestPasswordResetTest() {
    String email = "test@example.com";
    Usuario usuario = new Usuario();
    usuario.setUserId(1);
    usuario.setCorreoElectronico(email);
    Credencial credencial = new Credencial();

    when(iusuarioDataProvider.findByCorreoElectronico(email)).thenReturn(usuario);
    when(icredencialDataProvider.findByUserId(anyInt())).thenReturn(credencial);
    when(icredencialDataProvider.save(any(Credencial.class))).thenReturn(credencial);

    Boolean result = credencialService.requestPasswordReset(email);

    assertTrue(result);
    verify(icredencialDataProvider).save(any(Credencial.class));
    verify(messageSenderService).sendMessage(anyString(), any(UserModelMacro.class));
  }

  @Test
  public void updatePasswordTest() throws Exception {
    CredencialData credencialData = new CredencialData();
    String token = "dummyToken";
    Credencial credencial = new Credencial();

    when(icredencialDataProvider.login(anyInt())).thenReturn(credencial);
    when(icredencialDataProvider.save(any(Credencial.class))).thenReturn(credencial);

    Boolean result = credencialService.updatePassword(credencialData, token);

    assertTrue(result);
    verify(icredencialDataProvider).save(any(Credencial.class));
  }

  @Test
  public void createPasswordTest() throws Exception {
    RegistrarCredencialDto credencialData = new RegistrarCredencialDto();
    String token = "dummyToken";
    Credencial credencial = new Credencial();

    when(icredencialDataProvider.login(anyInt())).thenReturn(credencial);
    when(icredencialDataProvider.save(any(Credencial.class))).thenReturn(credencial);

    Boolean result = credencialService.createPassword(credencialData, token);

    assertTrue(result);
    verify(icredencialDataProvider).save(any(Credencial.class));
  }


}