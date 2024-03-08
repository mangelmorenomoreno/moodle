package com.prueba.carvajal.modules.credencial.dataproviders;

import com.prueba.carvajal.crosscutting.persistence.entity.Credencial;

public interface ICredencialDataProvider {

  public Credencial login(Integer userId);
  public Credencial findByUserId(Integer userId);

  public Credencial save(Credencial credencial);
}
