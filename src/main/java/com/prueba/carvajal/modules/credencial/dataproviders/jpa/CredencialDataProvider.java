package com.prueba.carvajal.modules.credencial.dataproviders.jpa;

import com.prueba.carvajal.crosscutting.persistence.entity.Credencial;
import com.prueba.carvajal.crosscutting.persistence.repository.CredencialRepository;
import com.prueba.carvajal.modules.credencial.dataproviders.ICredencialDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredencialDataProvider implements ICredencialDataProvider {

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
