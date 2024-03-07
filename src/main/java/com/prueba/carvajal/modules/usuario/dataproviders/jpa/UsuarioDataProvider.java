package com.prueba.carvajal.modules.usuario.dataproviders.jpa;

import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import com.prueba.carvajal.crosscutting.persistence.repository.UsuarioRepository;
import com.prueba.carvajal.modules.usuario.dataproviders.IUsuarioDataProvider;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UsuarioDataProvider
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Log4j2
@Service
public class UsuarioDataProvider implements IUsuarioDataProvider {

  private final UsuarioRepository usuarioRepository;

  @Autowired
  public UsuarioDataProvider(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

    @Override
  public Usuario findByUserId(Integer userId) {
    return usuarioRepository.findByUserId(userId);
  }

  @Override
  public Usuario findByCorreoElectronico(String correoElectronico) {
    return usuarioRepository.findByCorreoElectronico(correoElectronico);
  }
}
