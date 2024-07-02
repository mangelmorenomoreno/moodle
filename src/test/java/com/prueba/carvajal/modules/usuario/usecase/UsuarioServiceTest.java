package com.prueba.carvajal.modules.usuario.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ucundinamarca.crosscutting.domain.dto.user.BasicInformationUserDto;
import com.ucundinamarca.crosscutting.domain.dto.user.UserModelMacro;
import com.ucundinamarca.crosscutting.persistence.entity.Credencial;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import com.ucundinamarca.crosscutting.utils.TokenGenerator;
import com.ucundinamarca.modules.credencial.dataproviders.IcredencialDataProvider;
import com.ucundinamarca.modules.sendmail.usecase.message.MessageSenderService;
import com.ucundinamarca.modules.usuario.dataproviders.IusuarioDataProvider;
import com.ucundinamarca.modules.usuario.usecase.UsuarioService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;



/**
 * UsuarioController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
  static String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJDYXJ2YWphbC"
                          + "IsInN1YiI6IkpXVCBUb2tlbiIsInVzZXJuYW"
                          + "1lIjoiTWlndWVsIEFuZ2VsICBNb3Jlbm8gT"
                          + "W9yZW5vICIsIm1haWwiOiJtaWd1ZWwzMzNtbW1AZ21"
                          + "haWwuY29tIiwiaWRVc2VyIjoyOSwiaWF0Ijox"
                          + "NzEwMTYyODE2LCJleHAiOjE3MTA1MjI4MTZ9.KZX"
                          + "-Sky2RXlFcgf3kNdOUu4CoI5dHPxPPBZKNK7VR9Y";
  @Mock
  private IusuarioDataProvider iusuarioDataProvider;

  @Mock
  private IcredencialDataProvider icredencialDataProvider;

  @Mock
  private MessageSenderService messageSenderService;

  @InjectMocks
  private UsuarioService usuarioService;

  @Before
  public void setUp() {

  }

  @Test
  public void testGetFindByUserId() throws Exception {

    Usuario expectedUser = new Usuario();
    expectedUser.setUserId(1);

    when(TokenGenerator.getIdUserFromToken(token)).thenReturn("1");
    when(iusuarioDataProvider.findByUserId(1)).thenReturn(expectedUser);

    Usuario resultUser = usuarioService.getFindByUserId(token);

    assertEquals(expectedUser, resultUser);
    verify(iusuarioDataProvider).findByUserId(1);
  }

  @Test
  public void saveUserTest() throws Exception {
    // Arrange
    BasicInformationUserDto basicInformationUserDto = new BasicInformationUserDto();
    Usuario expectedUsuario = new Usuario();
    when(iusuarioDataProvider.save(any(Usuario.class))).thenReturn(expectedUsuario);

    // Act
    Boolean result = usuarioService.save(basicInformationUserDto);

    // Assert
    assertTrue(result);
    verify(iusuarioDataProvider).save(any(Usuario.class));
    verify(icredencialDataProvider).save(any(Credencial.class));
    verify(messageSenderService).sendMessage(anyString(), any(UserModelMacro.class));
  }

  @Test
  public void getFindByUserIdTest() throws Exception {
    // Arrange
    String token = "testToken";
    Usuario expectedUsuario = new Usuario(); // Asume un constructor o builder
    when(iusuarioDataProvider.findByUserId(anyInt())).thenReturn(expectedUsuario);

    // Act
    Usuario result = usuarioService.getFindByUserId(token);

    // Assert
    assertEquals(expectedUsuario, result);
  }
}