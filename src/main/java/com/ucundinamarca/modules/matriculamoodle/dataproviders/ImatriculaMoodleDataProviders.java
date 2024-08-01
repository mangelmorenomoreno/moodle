package com.ucundinamarca.modules.matriculamoodle.dataproviders;

import com.ucundinamarca.crosscutting.domain.dto.moodle.DocentesMatriculaVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.EstudiantesMatriculaMoodleVo;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.MatriculaMoodle;
import java.util.List;

/**
 * IMatriculaMoodleDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */

public interface ImatriculaMoodleDataProviders {

  List<EstudiantesMatriculaMoodleVo> listarEstudiantesMatriculaMasiva(
      String progId, String unidId, String peunId, String niedId, String grupId,
      String pegeId, String documento, String instId, String codMateria, String idMoodle)
      throws Exception;

  List<MatriculaMoodle> saveAll(List<MatriculaMoodle> matriculaMoodles);

  MatriculaMoodle save(MatriculaMoodle matriculaMoodles);

  List<EstudiantesMatriculaMoodleVo> listarDesmatriculaEstudiantes(
      String instId, String pegeId, String grupId, String peunId, String documento
  ) throws Exception;

  void delete(MatriculaMoodle matriculaMoodles) throws Exception;

  List<DocentesMatriculaVo> docenteMatricula(String grupId, String pegeId, String documento,
                                             String peunId, String usuario, String programa,
                                             String unidad, String instancia) throws Exception;

}
