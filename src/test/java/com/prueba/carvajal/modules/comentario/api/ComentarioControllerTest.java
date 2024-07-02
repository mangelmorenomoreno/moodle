package com.prueba.carvajal.modules.comentario.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.ucundinamarca.crosscutting.domain.dto.comentario.ComentarioDto;
import com.ucundinamarca.crosscutting.patterns.IrestResponse;
import com.ucundinamarca.crosscutting.persistence.entity.Comentario;
import com.ucundinamarca.modules.comentario.api.ComentarioController;
import com.ucundinamarca.modules.comentario.usecase.ComentarioService;
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
 * CredencialControllerTest.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
public class ComentarioControllerTest {

  @InjectMocks
  private ComentarioController comentarioController;

  @Mock
  private ComentarioService comentarioService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void crearPublicacionTest() throws Exception {
    ComentarioDto comentarioDto = ComentarioDto.builder().build();
    String token = "dummyToken";
    when(comentarioService.save(comentarioDto, token)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response =
        comentarioController.crearPublicacion(comentarioDto, token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  public void listarPublicacionesTest() throws Exception {
    String token = "dummyToken";
    List<Comentario> comentarios = List.of(new Comentario());
    when(comentarioService.findPublicacion()).thenReturn(comentarios);

    ResponseEntity<IrestResponse<List<Comentario>>> response =
        comentarioController.listarPublicaciones(token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(comentarios, response.getBody().getData());
  }

  @Test
  public void eliminarComentarioTest() throws Exception {
    Integer commentId = 1;
    String token = "dummyToken";
    when(comentarioService.delete(commentId)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response =
        comentarioController.eliminarComentario(commentId, token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }


}