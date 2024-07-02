package com.prueba.carvajal.modules.respuestacomentario.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ucundinamarca.crosscutting.domain.dto.respuesta.RespuestaComentarioDto;
import com.ucundinamarca.crosscutting.persistence.entity.Comentario;
import com.ucundinamarca.crosscutting.persistence.entity.RespuestaComentario;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import com.ucundinamarca.modules.comentario.dataproviders.IcomentarioDataProviders;
import com.ucundinamarca.modules.respuestacomentario.dataproviders.IrespuestaComentarioDataProvider;
import com.ucundinamarca.modules.respuestacomentario.usecase.RespuestaComentarioService;
import com.ucundinamarca.modules.usuario.dataproviders.IusuarioDataProvider;
import java.util.Collections;
import java.util.List;
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
public class RespuestaComentarioServiceTest {
  @InjectMocks
  private RespuestaComentarioService respuestaComentarioService;

  @Mock
  private IusuarioDataProvider iusuarioDataProvider;

  @Mock
  private IrespuestaComentarioDataProvider irespuestaComentarioDataProvider;

  @Mock
  private IcomentarioDataProviders icomentarioDataProviders;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void saveTest() throws Exception {
    RespuestaComentarioDto dto = mock(RespuestaComentarioDto.class);
    String token = "dummyToken";

    when(iusuarioDataProvider.findByUserId(anyInt())).thenReturn(new Usuario());
    when(icomentarioDataProviders.findCommentId(anyInt())).thenReturn(new Comentario());

    Boolean result = respuestaComentarioService.save(dto, token);

    assertTrue(result);
    verify(irespuestaComentarioDataProvider).save(any(RespuestaComentario.class));
  }

  @Test
  public void updateTest() {
    RespuestaComentario comentario = new RespuestaComentario();
    when(irespuestaComentarioDataProvider.update(comentario)).thenReturn(comentario);

    RespuestaComentario result = respuestaComentarioService.update(comentario);

    assertEquals(comentario, result);
    verify(irespuestaComentarioDataProvider).update(comentario);
  }

  @Test
  public void deleteTest() {
    Integer respuestaId = 1;
    when(irespuestaComentarioDataProvider.findById(respuestaId))
        .thenReturn((List<RespuestaComentario>) new RespuestaComentario());

    Boolean result = respuestaComentarioService.delete(respuestaId);

    assertTrue(result);
    verify(irespuestaComentarioDataProvider).delete(
        Collections.singletonList(any(RespuestaComentario.class)));
  }

  @Test
  public void findByIdTest() {
    Integer respuestaId = 1;
    List<RespuestaComentario> expectedList = List.of(new RespuestaComentario());
    when(irespuestaComentarioDataProvider.findById(respuestaId)).thenReturn(expectedList);

    List<RespuestaComentario> result = respuestaComentarioService.findById(respuestaId);

    assertEquals(expectedList, result);
  }

  @Test
  public void findByAllTest() {
    List<RespuestaComentario> expectedList = List.of(new RespuestaComentario());
    when(irespuestaComentarioDataProvider.findByAll()).thenReturn(expectedList);

    List<RespuestaComentario> result = respuestaComentarioService.findByAll();

    assertEquals(expectedList, result);
  }



}