package com.ucundinamarca.modules.gruposemilla.usecase;

import com.ucundinamarca.modules.gruposemilla.dataproviders.IgrupoSemillaDataProviders;
import com.ucundinamarca.modules.reporteador.dataproviders.IreporteadorDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * GrupoSemillaService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */

@Log4j2
@Service
public class GrupoSemillaService {


  @Autowired
  private IgrupoSemillaDataProviders igrupoSemillaDataProviders;


  @Autowired
  private IreporteadorDataProviders ireporteadorDataProviders;


}
