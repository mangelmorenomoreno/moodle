package com.prueba.carvajal.modules.respuestacomentario.dataproviders.jpa;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prueba.carvajal.crosscutting.persistence.entity.Comentario;
import com.prueba.carvajal.crosscutting.persistence.entity.RespuestaComentario;
import com.prueba.carvajal.crosscutting.persistence.repository.RespuestaComentarioRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class RespuestaComentarioDataProvidersTest {

  @InjectMocks
  private RespuestaComentarioDataProviders dataProviders;

  @Mock
  private RespuestaComentarioRepository comentarioRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void saveTest() {
    RespuestaComentario comentario = new RespuestaComentario();
    when(comentarioRepository.save(comentario)).thenReturn(comentario);

    RespuestaComentario result = dataProviders.save(comentario);

    assertEquals(comentario, result);
    verify(comentarioRepository).save(comentario);
  }

  @Test
  public void updateTest() {
    RespuestaComentario comentario = new RespuestaComentario();
    when(comentarioRepository.save(comentario)).thenReturn(comentario);

    RespuestaComentario result = dataProviders.update(comentario);

    assertEquals(comentario, result);
    verify(comentarioRepository).save(comentario);
  }

  @Test
  public void deleteTest() {
    List<RespuestaComentario> comentarios = List.of(new RespuestaComentario());

    Boolean result = dataProviders.delete(comentarios);

    assertTrue(result);
    verify(comentarioRepository, times(comentarios.size())).delete(any(RespuestaComentario.class));
  }

  @Test
  public void findByIdTest() {
    Integer respuestaId = 1;
    RespuestaComentario comentario = new RespuestaComentario();
    when(comentarioRepository.findById(respuestaId)).thenReturn(Optional.of(comentario));

    List<RespuestaComentario> result = dataProviders.findById(respuestaId);

    assertEquals(1, result.size());
    assertEquals(comentario, result.get(0));
  }

  @Test
  public void findByAllTest() {
    List<RespuestaComentario> expectedList = List.of(new RespuestaComentario());
    when(comentarioRepository.findAll()).thenReturn(expectedList);

    List<RespuestaComentario> result = dataProviders.findByAll();

    assertEquals(expectedList, result);
  }

  @Test
  public void findByComentarioTest() {
    Comentario comentario = new Comentario();
    List<RespuestaComentario> expectedList = List.of(new RespuestaComentario());
    when(comentarioRepository.findByComentario(comentario)).thenReturn(expectedList);

    List<RespuestaComentario> result = dataProviders.findByComentario(comentario);

    assertEquals(expectedList, result);
  }




}