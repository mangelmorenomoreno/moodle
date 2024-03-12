package com.prueba.carvajal.modules.respuestacomentario.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.prueba.carvajal.crosscutting.domain.dto.respuesta.RespuestaComentarioDto;
import com.prueba.carvajal.crosscutting.patterns.IrestResponse;
import com.prueba.carvajal.crosscutting.persistence.entity.RespuestaComentario;
import com.prueba.carvajal.modules.respuestacomentario.usecase.RespuestaComentarioService;
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
public class RespuestaComentarioControllerTest {

  @InjectMocks
  private RespuestaComentarioController respuestaComentarioController;

  @Mock
  private RespuestaComentarioService comentarioService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void listarPublicacionesTest() throws Exception {
    String token = "dummyToken";
    List<RespuestaComentario> comentarios = List.of(new RespuestaComentario());
    when(comentarioService.findByAll()).thenReturn(comentarios);

    ResponseEntity<IrestResponse<List<RespuestaComentario>>> response =
        respuestaComentarioController.listarPublicaciones(token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody().getData());
  }

  @Test
  public void guardarComentariosTest() throws Exception {
    RespuestaComentarioDto dto = RespuestaComentarioDto.builder().build();
    String token = "dummyToken";
    when(comentarioService.save(dto, token)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response =
        respuestaComentarioController.guardarComentarios(dto, token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  public void eliminarRespuestasTest() throws Exception {
    Integer respuestaId = 1;
    String token = "dummyToken";
    when(comentarioService.delete(respuestaId)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response =
        respuestaComentarioController.eliminarRespuestas(respuestaId, token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }



}