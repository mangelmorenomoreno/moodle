package com.prueba.carvajal.modules.comentario.dataproviders.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.prueba.carvajal.crosscutting.persistence.entity.Comentario;
import com.prueba.carvajal.crosscutting.persistence.repository.ComentarioRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * ComentarioDataProvidersTest.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
public class ComentarioDataProvidersTest {

  @InjectMocks
  private ComentarioDataProviders comentarioDataProviders;

  @Mock
  private ComentarioRepository comentarioRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void deleteIndividualTest() {
    Comentario comentario = new Comentario();

    Boolean result = comentarioDataProviders.delete(comentario);

    assertTrue(result);
    verify(comentarioRepository).delete(comentario);
  }

  @Test
  public void deleteListTest() {
    List<Comentario> comentarios = List.of(new Comentario());

    Boolean result = comentarioDataProviders.delete(comentarios);

    assertTrue(result);
    verify(comentarioRepository, times(comentarios.size())).delete(any(Comentario.class));
  }

  @Test
  public void saveTest() {
    Comentario comentario = new Comentario();
    when(comentarioRepository.save(comentario)).thenReturn(comentario);

    Comentario result = comentarioDataProviders.save(comentario);

    assertEquals(comentario, result);
    verify(comentarioRepository).save(comentario);
  }


  @Test
  public void updateTest() {
    Comentario comentario = new Comentario();
    when(comentarioRepository.save(comentario)).thenReturn(comentario);

    Comentario result = comentarioDataProviders.update(comentario);

    assertEquals(comentario, result);
    verify(comentarioRepository).save(comentario);
  }

  @Test
  public void findCommentIdTest() {
    Integer commentId = 1;
    Comentario comentario = new Comentario();
    List<Comentario> comentarios = List.of(comentario);
    when(comentarioRepository.findByCommentId(commentId)).thenReturn(comentarios);

    Comentario result = comentarioDataProviders.findCommentId(commentId);

    assertEquals(comentario, result);
  }


}