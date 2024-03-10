package com.prueba.carvajal.modules.credencial.dataproviders.jpa;

import com.prueba.carvajal.crosscutting.persistence.entity.Credencial;
import com.prueba.carvajal.crosscutting.persistence.repository.CredencialRepository;
import com.prueba.carvajal.modules.credencial.dataproviders.IcredencialDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * CredencialDataProvider.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */

@Service
public class CredencialDataProvider implements IcredencialDataProvider {

  @Autowired
  private CredencialRepository credencialRepository;

  @Override
  public Credencial login(Integer userId) {
    return credencialRepository.findByUsuarioUserId(userId).get();
  }

  @Override
  public Credencial findByUserId(Integer userId) {
    return credencialRepository.findByUsuarioUserId(userId).get();
  }

  @Override
  public Credencial save(Credencial credencial) {
    return credencialRepository.save(credencial);
  }
}
