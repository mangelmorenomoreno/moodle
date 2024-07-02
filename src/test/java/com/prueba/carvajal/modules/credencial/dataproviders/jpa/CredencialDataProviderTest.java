package com.prueba.carvajal.modules.credencial.dataproviders.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ucundinamarca.crosscutting.persistence.entity.Credencial;
import com.ucundinamarca.crosscutting.persistence.repository.CredencialRepository;
import java.util.Optional;

import com.ucundinamarca.modules.credencial.dataproviders.jpa.CredencialDataProvider;
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
public class CredencialDataProviderTest {

  @InjectMocks
  private CredencialDataProvider credencialDataProvider;

  @Mock
  private CredencialRepository credencialRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void loginTest() {
    Integer userId = 1;
    Credencial expectedCredencial = new Credencial();
    when(credencialRepository.findByUsuarioUserId(userId))
        .thenReturn(Optional.of(expectedCredencial));

    Credencial result = credencialDataProvider.login(userId);

    assertEquals(expectedCredencial, result);
    verify(credencialRepository).findByUsuarioUserId(userId);
  }

  @Test
  public void findByUserIdTest() {
    Integer userId = 1;
    Credencial expectedCredencial = new Credencial();
    when(credencialRepository.findByUsuarioUserId(userId))
        .thenReturn(Optional.of(expectedCredencial));

    Credencial result = credencialDataProvider.findByUserId(userId);

    assertEquals(expectedCredencial, result);
    verify(credencialRepository).findByUsuarioUserId(userId);
  }

  @Test
  public void saveTest() {
    Credencial credencial = new Credencial();
    when(credencialRepository.save(credencial)).thenReturn(credencial);

    Credencial result = credencialDataProvider.save(credencial);

    assertEquals(credencial, result);
    verify(credencialRepository).save(credencial);
  }


}