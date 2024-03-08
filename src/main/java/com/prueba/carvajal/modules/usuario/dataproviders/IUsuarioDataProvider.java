package com.prueba.carvajal.modules.usuario.dataproviders;

import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import java.util.List;

/**
 * IUsuarioDataProvider
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

public interface IUsuarioDataProvider {

  public Usuario findByUserId(Integer userId);

  public Usuario findByCorreoElectronico(String email);

  public Usuario save(Usuario usuario);

  public List<Usuario> findByNombreAndCorreoElectronicoSimilar(
      String valor);

}
