package com.prueba.carvajal.modules.usuario.dataproviders.jpa;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import com.ucundinamarca.crosscutting.persistence.repository.UsuarioRepository;
import java.util.List;

import com.ucundinamarca.modules.usuario.dataproviders.jpa.UsuarioDataProvider;
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
public class UsuarioDataProviderTest  {
  @InjectMocks
  private UsuarioDataProvider usuarioDataProvider;

  @Mock
  private UsuarioRepository usuarioRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void findByUserIdTest() {
    // Arrange
    Integer userId = 1;
    Usuario expectedUsuario = new Usuario();
    when(usuarioRepository.findByUserId(userId)).thenReturn(expectedUsuario);

    // Act
    Usuario result = usuarioDataProvider.findByUserId(userId);

    // Assert
    assertEquals(expectedUsuario, result);
  }

  @Test
  public void findByCorreoElectronicoTest() {
    // Arrange
    String email = "test@example.com";
    Usuario expectedUsuario = new Usuario();
    when(usuarioRepository.findByCorreoElectronico(email)).thenReturn(expectedUsuario);

    // Act
    Usuario result = usuarioDataProvider.findByCorreoElectronico(email);

    // Assert
    assertEquals(expectedUsuario, result);
  }

  @Test
  public void saveTest() {
    // Arrange
    Usuario usuario = new Usuario();
    when(usuarioRepository.save(usuario)).thenReturn(usuario);

    // Act
    Usuario result = usuarioDataProvider.save(usuario);

    // Assert
    assertEquals(usuario, result);
  }

  @Test
  public void findByNombreAndCorreoElectronicoSimilarTest() {
    // Arrange
    String valor = "test";
    List<Usuario> expectedList = List.of(new Usuario());
    when(usuarioRepository.findByNombreAndCorreoElectronicoSimilar(valor)).thenReturn(expectedList);

    // Act
    List<Usuario> result = usuarioDataProvider.findByNombreAndCorreoElectronicoSimilar(valor);

    // Assert
    assertEquals(expectedList, result);
  }

}