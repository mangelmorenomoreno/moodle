package com.ucundinamarca.modules.credencial.dataproviders;

import com.ucundinamarca.crosscutting.persistence.entity.Credencial;

/**
 * IcredencialDataProvider.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
public interface IcredencialDataProvider {
  public Credencial login(Integer userId);

  public Credencial findByUserId(Integer userId);

  public Credencial save(Credencial credencial);
}
