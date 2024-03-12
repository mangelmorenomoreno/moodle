package com.prueba.carvajal.modules.publicacion.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prueba.carvajal.crosscutting.domain.dto.publicacion.PublicacionDto;
import com.prueba.carvajal.crosscutting.persistence.entity.Comentario;
import com.prueba.carvajal.crosscutting.persistence.entity.Publicacion;
import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import com.prueba.carvajal.modules.comentario.dataproviders.IcomentarioDataProviders;
import com.prueba.carvajal.modules.publicacion.dataproviders.IpublicacionDataProvider;
import com.prueba.carvajal.modules.respuestacomentario.dataproviders.IrespuestaComentarioDataProvider;
import com.prueba.carvajal.modules.usuario.dataproviders.IusuarioDataProvider;
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
public class PublicacionServiceTest {

  @InjectMocks
  private PublicacionService publicacionService;

  @Mock
  private IpublicacionDataProvider ipublicacionDataProvider;

  @Mock
  private IusuarioDataProvider iusuarioDataProvider;

  @Mock
  private IcomentarioDataProviders icomentarioDataProviders;

  @Mock
  private IrespuestaComentarioDataProvider irespuestaComentarioDataProvider;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void saveTest() throws Exception {
    PublicacionDto dto = PublicacionDto.builder().build();
    dto.setTitulo("Titulo de prueba");
    dto.setContenido("Contenido de prueba");
    String token = "dummyToken";

    when(iusuarioDataProvider.findByUserId(anyInt())).thenReturn(new Usuario());
    when(ipublicacionDataProvider.save(any(Publicacion.class))).thenReturn(new Publicacion());

    Boolean result = publicacionService.save(dto, token);

    assertTrue(result);
    verify(ipublicacionDataProvider).save(any(Publicacion.class));
  }

  @Test
  public void findAllTest() {
    List<Publicacion> expectedList = List.of(new Publicacion());
    when(ipublicacionDataProvider.findAll()).thenReturn(expectedList);

    List<Publicacion> result = publicacionService.findAll();

    assertEquals(expectedList, result);
  }

  @Test
  public void updateTest() throws Exception {
    PublicacionDto dto = PublicacionDto.builder().build();
    dto.setPostId(1L);
    dto.setTitulo("Titulo actualizado");
    dto.setContenido("Contenido actualizado");
    String token = "dummyToken";

    when(iusuarioDataProvider.findByUserId(anyInt())).thenReturn(new Usuario());
    when(ipublicacionDataProvider.update(any(Publicacion.class))).thenReturn(new Publicacion());

    Boolean result = publicacionService.update(dto, token);

    assertTrue(result);
    verify(ipublicacionDataProvider).update(any(Publicacion.class));
  }

  @Test
  public void deleteTest() {
    Integer postId = 1;
    when(ipublicacionDataProvider.findById(postId)).thenReturn(new Publicacion());
    when(icomentarioDataProviders.findPublicacion(any(Publicacion.class)))
        .thenReturn(List.of(new Comentario()));

    Boolean result = publicacionService.delete(postId);

    assertTrue(result);
    verify(ipublicacionDataProvider).delete(any(Publicacion.class));
    verify(icomentarioDataProviders).delete(anyList());
    verify(irespuestaComentarioDataProvider).delete(anyList());
  }



}