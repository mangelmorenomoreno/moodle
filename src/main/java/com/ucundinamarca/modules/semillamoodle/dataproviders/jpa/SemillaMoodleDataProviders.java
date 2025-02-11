package com.ucundinamarca.modules.semillamoodle.dataproviders.jpa;

import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.repository.SemillaMoodleRepository;
import com.ucundinamarca.modules.semillamoodle.dataproviders.IsemillaMoodleDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SemillaMoodleDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */

@Log4j2
@Service
public class SemillaMoodleDataProviders implements IsemillaMoodleDataProviders {

  @Autowired
  private SemillaMoodleRepository jpaSemillaMoodleRepository;


}
