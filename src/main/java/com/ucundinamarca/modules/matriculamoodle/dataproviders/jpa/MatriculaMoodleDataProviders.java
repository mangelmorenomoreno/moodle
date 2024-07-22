package com.ucundinamarca.modules.matriculamoodle.dataproviders.jpa;

import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.repository.MatriculaMoodleRepository;
import com.ucundinamarca.modules.matriculamoodle.dataproviders.ImatriculaMoodleDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MatriculaMoodleDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */

@Log4j2
@Service
public class MatriculaMoodleDataProviders implements ImatriculaMoodleDataProviders {

  @Autowired
  private MatriculaMoodleRepository jpaMatriculaMoodleRepository;


}
