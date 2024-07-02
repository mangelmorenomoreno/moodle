package com.prueba.carvajal.modules.comentario.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ucundinamarca.crosscutting.domain.dto.comentario.ComentarioDto;
import com.ucundinamarca.crosscutting.persistence.entity.Comentario;
import com.ucundinamarca.crosscutting.persistence.entity.Publicacion;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import com.ucundinamarca.modules.comentario.dataproviders.IcomentarioDataProviders;
import com.ucundinamarca.modules.comentario.usecase.ComentarioService;
import com.ucundinamarca.modules.publicacion.dataproviders.IpublicacionDataProvider;
import com.ucundinamarca.modules.usuario.dataproviders.IusuarioDataProvider;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


/**
 * ComentarioServiceTest.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
public class ComentarioServiceTest {

  @InjectMocks
  private ComentarioService comentarioService;

  @Mock
  private IcomentarioDataProviders icomentarioDataProviders;

  @Mock
  private IpublicacionDataProvider ipublicacionDataProvider;

  @Mock
  private IusuarioDataProvider iusuarioDataProvider;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void deleteTest() {
    Integer commentId = 1;
    Comentario comentario = new Comentario();
    when(icomentarioDataProviders.findCommentId(commentId)).thenReturn(comentario);
    when(icomentarioDataProviders.delete(comentario)).thenReturn(true);

    Boolean result = comentarioService.delete(commentId);

    assertTrue(result);
    verify(icomentarioDataProviders).delete(comentario);
  }

  @Test
  public void saveTest() throws Exception {
    ComentarioDto comentarioDto = ComentarioDto.builder().build();
    comentarioDto.setContenido("Contenido de prueba");
    String token = "dummyToken";
    Usuario usuario = new Usuario();
    Publicacion publicacion = new Publicacion();

    when(iusuarioDataProvider.findByUserId(anyInt())).thenReturn(usuario);
    when(ipublicacionDataProvider.findById(anyInt())).thenReturn(publicacion);
    when(icomentarioDataProviders.save(any(Comentario.class))).thenReturn(new Comentario());

    Boolean result = comentarioService.save(comentarioDto, token);

    assertTrue(result);
    verify(icomentarioDataProviders).save(any(Comentario.class));
  }

  @Test
  public void updateTest() {
    Comentario comentario = new Comentario();
    when(icomentarioDataProviders.save(comentario)).thenReturn(comentario);

    Comentario result = comentarioService.update(comentario);

    assertEquals(comentario, result);
    verify(icomentarioDataProviders).save(comentario);
  }

}