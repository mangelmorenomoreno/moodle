package com.ucundinamarca.modules.usuario.dataproviders;

import com.ucundinamarca.crosscutting.persistence.entity.Usuario;

import java.util.List;

/**
 * IUsuarioDataProvider.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

public interface IusuarioDataProvider {

  public Usuario findByUserId(Integer userId);

  public Usuario findByCorreoElectronico(String email);

  public Usuario save(Usuario usuario);

  public List<Usuario> findByNombreAndCorreoElectronicoSimilar(
      String valor);

}
