package com.prueba.carvajal.modules.sendmail.usecase.email;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ucundinamarca.crosscutting.persistence.entity.Credencial;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import com.ucundinamarca.modules.sendmail.usecase.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * UsuarioDataProviderTest.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

  @InjectMocks
  private EmailService emailService;

  @Mock
  private JavaMailSender mailSender;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void sendActivateEmailTest() {
    Credencial credencial = mock(Credencial.class);
    Usuario usuario = new Usuario(); // Asume un constructor o un builder
    usuario.setCorreoElectronico("test@example.com");
    when(credencial.getUsuario()).thenReturn(usuario);
    when(credencial.getTokenResetPassword()).thenReturn("token123");

    emailService.sendActivateEmail(credencial);

    ArgumentCaptor<SimpleMailMessage> messageCaptor =
        ArgumentCaptor.forClass(SimpleMailMessage.class);
    verify(mailSender).send(messageCaptor.capture());
    SimpleMailMessage sentMessage = messageCaptor.getValue();

    assertEquals("test@example.com", sentMessage.getTo()[0]);
    assertTrue(sentMessage.getText().contains("token123"));
  }

  @Test
  public void sendRecuperarPasswordTest() {
    Credencial credencial = mock(Credencial.class);
    Usuario usuario = new Usuario();
    usuario.setCorreoElectronico("test@example.com");
    when(credencial.getUsuario()).thenReturn(usuario);
    when(credencial.getTokenResetPassword()).thenReturn("token123");

    emailService.sendRecuperarPassword(credencial);

    ArgumentCaptor<SimpleMailMessage> messageCaptor =
        ArgumentCaptor.forClass(SimpleMailMessage.class);
    verify(mailSender).send(messageCaptor.capture());
    SimpleMailMessage sentMessage = messageCaptor.getValue();

    assertEquals("test@example.com", sentMessage.getTo()[0]);
    assertTrue(sentMessage.getText().contains("token123"));
  }


}