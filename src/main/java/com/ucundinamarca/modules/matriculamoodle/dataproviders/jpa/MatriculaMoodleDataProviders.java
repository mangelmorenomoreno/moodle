package com.ucundinamarca.modules.matriculamoodle.dataproviders.jpa;

import com.ucundinamarca.crosscutting.domain.dto.moodle.EstudiantesMatriculaMoodleVo;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.MatriculaMoodle;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.repository.MatriculaMoodleRepository;
import com.ucundinamarca.crosscutting.persistence.reporteador.repository.MatriculaMoodleReporteadorRepository;
import com.ucundinamarca.modules.matriculamoodle.dataproviders.ImatriculaMoodleDataProviders;
import java.util.List;
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

  @Autowired
  private MatriculaMoodleReporteadorRepository matriculaMoodleReporteadorRepository;


  @Override
  public List<EstudiantesMatriculaMoodleVo> listarEstudiantesMatriculaMasiva(
      String progId, String unidId, String peunId, String niedId, String grupId, String pegeId,
      String documento, String instId, String codMateria, String idMoodle) throws Exception {
    return matriculaMoodleReporteadorRepository.listarEstudiantesMatriculaMasiva(progId, unidId,
        peunId, niedId, instId, pegeId, documento, instId, codMateria, idMoodle);
  }

  @Override
  public List<MatriculaMoodle> saveAll(List<MatriculaMoodle> matriculaMoodles) {
    return jpaMatriculaMoodleRepository.saveAll(matriculaMoodles);
  }

  @Override
  public MatriculaMoodle save(MatriculaMoodle matriculaMoodles) {
    return jpaMatriculaMoodleRepository.save(matriculaMoodles);
  }


}
