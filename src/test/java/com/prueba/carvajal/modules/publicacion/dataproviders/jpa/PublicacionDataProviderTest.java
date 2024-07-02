package com.prueba.carvajal.modules.publicacion.dataproviders.jpa;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ucundinamarca.crosscutting.persistence.entity.Publicacion;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import com.ucundinamarca.crosscutting.persistence.repository.PublicacionRepository;
import java.util.List;

import com.ucundinamarca.modules.publicacion.dataproviders.jpa.PublicacionDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * PublicacionDataProviderTest.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
public class PublicacionDataProviderTest {

  @InjectMocks
  private PublicacionDataProvider publicacionDataProvider;

  @Mock
  private PublicacionRepository publicacionRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void saveTest() {
    Publicacion publicacion = new Publicacion();
    when(publicacionRepository.save(publicacion)).thenReturn(publicacion);

    Publicacion result = publicacionDataProvider.save(publicacion);

    assertEquals(publicacion, result);
    verify(publicacionRepository).save(publicacion);
  }

  @Test
  public void findAllTest() {
    List<Publicacion> expectedList = List.of(new Publicacion());
    when(publicacionRepository.findAllOrderByFechaPublicacionDesc()).thenReturn(expectedList);

    List<Publicacion> result = publicacionDataProvider.findAll();

    assertEquals(expectedList, result);
  }

  @Test
  public void updateTest() {
    Publicacion publicacion = new Publicacion();
    when(publicacionRepository.save(publicacion)).thenReturn(publicacion);

    Publicacion result = publicacionDataProvider.update(publicacion);

    assertEquals(publicacion, result);
    verify(publicacionRepository).save(publicacion);
  }


  @Test
  public void deleteTest() {
    Publicacion publicacion = new Publicacion();

    Boolean result = publicacionDataProvider.delete(publicacion);

    assertTrue(result);
    verify(publicacionRepository).delete(publicacion);
  }

  @Test
  public void findByUsuarioTest() {
    Usuario usuario = new Usuario();
    List<Publicacion> expectedList = List.of(new Publicacion());
    when(publicacionRepository.findByUsuario(usuario)).thenReturn(expectedList);

    List<Publicacion> result = publicacionDataProvider.findByUsuario(usuario);

    assertEquals(expectedList, result);
  }

  @Test
  public void findByIdTest() {
    Integer id = 1;
    Publicacion expectedPublicacion = new Publicacion();
    when(publicacionRepository.findById(id)).thenReturn(java.util.Optional.of(expectedPublicacion));

    Publicacion result = publicacionDataProvider.findById(id);

    assertEquals(expectedPublicacion, result);
  }



}