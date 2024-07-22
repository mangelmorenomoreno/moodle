package com.ucundinamarca.modules.instanciamoodle.usecase;


import com.ucundinamarca.modules.instanciamoodle.dataproviders.IinstanciaMoodleDataProviders;
import com.ucundinamarca.modules.reporteador.dataproviders.IreporteadorDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InstanciaMoodleService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */

@Log4j2
@Service
public class InstanciaMoodleService {

  @Autowired
  private IinstanciaMoodleDataProviders instanciaMoodleDataProviders;

  @Autowired
  private IreporteadorDataProviders ireporteadorDataProviders;
}
