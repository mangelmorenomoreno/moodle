package com.prueba.carvajal.modules.publicacion.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.prueba.carvajal.crosscutting.domain.dto.publicacion.PublicacionDto;
import com.prueba.carvajal.crosscutting.patterns.IrestResponse;
import com.prueba.carvajal.crosscutting.persistence.entity.Publicacion;
import com.prueba.carvajal.modules.publicacion.usecase.PublicacionService;
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
 * PublicacionControllerTest.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
public class PublicacionControllerTest {

  @InjectMocks
  private PublicacionController publicacionController;

  @Mock
  private PublicacionService publicacionService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void crearPublicacionTest() throws Exception {
    PublicacionDto publicacionDto = PublicacionDto.builder().build();
    String token = "dummyToken";
    when(publicacionService.save(publicacionDto, token)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response =
        publicacionController.crearPublicacion(publicacionDto, token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  public void listarPublicacionesTest() throws Exception {
    String token = "dummyToken";
    List<Publicacion> publicaciones = List.of(new Publicacion());
    when(publicacionService.findAll()).thenReturn(publicaciones);

    ResponseEntity<IrestResponse<List<Publicacion>>> response =
        publicacionController.listarPublicaciones(token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(publicaciones, response.getBody().getData());
  }

  @Test
  public void eliminarPublicacionTest() throws Exception {
    Integer postId = 1;
    String token = "dummyToken";
    when(publicacionService.delete(postId)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response =
        publicacionController.eliminarPublicacion(postId, token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

  @Test
  public void actualizarPublicacionTest() throws Exception {
    PublicacionDto publicacionDto = PublicacionDto.builder().build();
    String token = "dummyToken";
    when(publicacionService.update(publicacionDto, token)).thenReturn(true);

    ResponseEntity<IrestResponse<Boolean>> response =
        publicacionController.actualizarPublicacion(publicacionDto, token);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().getData());
  }

}