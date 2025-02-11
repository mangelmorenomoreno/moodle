package com.ucundinamarca.modules.tipousuario.usecase;

import com.ucundinamarca.modules.reporteador.dataproviders.IreporteadorDataProviders;
import com.ucundinamarca.modules.tipousuario.dataproviders.ItipoUsuarioDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * TipoUsuarioService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@Service
public class TipoUsuarioService {

  @Autowired
  private ItipoUsuarioDataProviders itipoUsuarioDataProviders;

  @Autowired
  private IreporteadorDataProviders ireporteadorDataProviders;
}
