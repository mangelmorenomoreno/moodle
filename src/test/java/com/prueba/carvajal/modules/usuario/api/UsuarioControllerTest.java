package com.prueba.carvajal.modules.usuario.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.ucundinamarca.crosscutting.domain.dto.user.BasicInformationUserDto;
import com.ucundinamarca.crosscutting.patterns.IrestResponse;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import com.ucundinamarca.modules.sendmail.usecase.message.MessageSenderService;
import com.ucundinamarca.modules.usuario.api.UsuarioController;
import com.ucundinamarca.modules.usuario.usecase.UsuarioService;
import java.util.List;
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
 * UsuarioDataProviderTest.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
public class UsuarioControllerTest  {

  @InjectMocks
  private UsuarioController usuarioController;

  @Mock
  private UsuarioService usuarioService;

  @Mock
  private MessageSenderService messageSenderService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void getUsuarioTest() throws Exception {
    String token = "dummyToken";
    Usuario usuario = new Usuario(); // Asume un constructor o builder
    when(usuarioService.getFindByUserId(token)).thenReturn(usuario);

    ResponseEntity<IrestResponse<Usuario>> response = usuarioController.getUsuario(token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody().getData());
  }

  @Test
  public void findByEmailTest() throws Exception {
    String email = "test@example.com";
    Usuario usuario = new Usuario();
    when(usuarioService.findByCorreoElectronico(email)).thenReturn(usuario);

    ResponseEntity<IrestResponse<Usuario>> response = usuarioController.findByEmail(email);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody().getData());
  }

  @Test
  public void crearUsuarioTest() throws Exception {
    BasicInformationUserDto userDto = new BasicInformationUserDto(); // Asume setters apropiados
    when(usuarioService.save(userDto)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response = usuarioController.crearUsuario(userDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  public void actualizaUsuarioTest() throws Exception {
    BasicInformationUserDto userDto = new BasicInformationUserDto();
    String token = "dummyToken";
    when(usuarioService.update(userDto, token)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response = usuarioController.actualizaUsuario(userDto,
        token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  public void findByEmailOrNombreTest() throws Exception {
    String valor = "test";
    List<Usuario> usuarios = List.of(new Usuario());
    when(usuarioService.findByNombreAndCorreoLike(valor)).thenReturn(usuarios);

    ResponseEntity<IrestResponse<List<Usuario>>> response =
        usuarioController.findByEmailOrNombre(valor);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertFalse(response.getBody().getData().isEmpty());
  }




}