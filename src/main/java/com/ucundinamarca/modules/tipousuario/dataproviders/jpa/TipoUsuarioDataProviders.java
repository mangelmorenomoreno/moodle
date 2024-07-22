package com.ucundinamarca.modules.tipousuario.dataproviders.jpa;

import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.repository.TipoUsuarioRepository;
import com.ucundinamarca.modules.tipousuario.dataproviders.ItipoUsuarioDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TipoUsuarioDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */

@Log4j2
@Service
public class TipoUsuarioDataProviders implements ItipoUsuarioDataProviders {

  @Autowired
  private TipoUsuarioRepository jpaTipoUsuarioRepository;


}
