package com.ucundinamarca.modules.matriculamoodle.usecase;

import com.ucundinamarca.modules.matriculamoodle.dataproviders.ImatriculaMoodleDataProviders;
import com.ucundinamarca.modules.reporteador.dataproviders.IreporteadorDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * MatriculaMoodleService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@Service
public class MatriculaMoodleService {

  @Autowired
  private ImatriculaMoodleDataProviders imatriculaMoodleDataProviders;


  @Autowired
  private IreporteadorDataProviders ireporteadorDataProviders;

}
