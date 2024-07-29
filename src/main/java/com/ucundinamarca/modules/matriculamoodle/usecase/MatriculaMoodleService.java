package com.ucundinamarca.modules.matriculamoodle.usecase;

import com.ucundinamarca.crosscutting.domain.dto.moodle.EstudiantesMatriculaMoodleVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.MatriculaMoodlewsVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.RespuestaMatriculaMoodleVo;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.MatriculaMoodle;
import com.ucundinamarca.crosscutting.utils.Conexion;
import com.ucundinamarca.modules.matriculamoodle.dataproviders.ImatriculaMoodleDataProviders;
import com.ucundinamarca.modules.matriculamoodle.resttemplate.MatriculaMoodleRestTemplate;
import com.ucundinamarca.modules.reporteador.dataproviders.IreporteadorDataProviders;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
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


  @Autowired
  private MatriculaMoodleRestTemplate matriculaMoodleRestTemplate;

  @Autowired
  private Conexion conexion;

  private List<EstudiantesMatriculaMoodleVo> listarEstudiantesMatriculaMasiva(
      String progId, String unidId, String peunId, String niedId, String grupId,
      String pegeId, String documento, String instId, String codMateria, String idMoodle)
      throws Exception {
    return imatriculaMoodleDataProviders.listarEstudiantesMatriculaMasiva(progId, unidId, peunId,
        niedId, grupId, pegeId, documento, instId, codMateria, idMoodle);
  }

  /**
   * Matricula a los estudiantes en Moodle.
   *
   * @throws Exception si ocurre algún error durante el proceso.
   */
  public void matricularEstudianteMoodle() throws Exception {
    List<EstudiantesMatriculaMoodleVo> estudiantesMatriculaMoodleVos =
        imatriculaMoodleDataProviders.listarEstudiantesMatriculaMasiva(
            null, null, null, "1", null, null,
            null, null, null, null);
    Timestamp fechaCambio = new Timestamp(System.currentTimeMillis());
    for (EstudiantesMatriculaMoodleVo estudiante : estudiantesMatriculaMoodleVos) {
      MatriculaMoodlewsVo matriculaMoodlewsVo = crearMatriculaMoodleVo(estudiante);
      RespuestaMatriculaMoodleVo respuesta = matriculaMoodleRestTemplate.matriculaMoodle(
          conexion.conexionPregradoMatriculaMoodle(), matriculaMoodlewsVo);
      if (respuesta.isEjecucion()) {
        guardarMatricula(estudiante, fechaCambio);
      }
    }
  }

  private MatriculaMoodlewsVo crearMatriculaMoodleVo(EstudiantesMatriculaMoodleVo estudiante)
      throws Exception {
    MatriculaMoodlewsVo matriculaMoodlewsVo = new MatriculaMoodlewsVo();
    matriculaMoodlewsVo.setCourseid(URLEncoder.encode(estudiante.getGrseIdMoodle(),
        StandardCharsets.UTF_8.toString()));
    matriculaMoodlewsVo.setRoleid(URLEncoder.encode("5",
        StandardCharsets.UTF_8.toString()));
    matriculaMoodlewsVo.setUserid(URLEncoder.encode(estudiante.getUsmoIdMoodle(),
        StandardCharsets.UTF_8.toString()));
    return matriculaMoodlewsVo;
  }

  private void guardarMatricula(EstudiantesMatriculaMoodleVo estudiante, Timestamp fechaCambio) {
    MatriculaMoodle matriculaMoodle = new MatriculaMoodle();
    matriculaMoodle.setGrseId(Long.valueOf(estudiante.getGrseId()));
    matriculaMoodle.setUsmoId(Long.valueOf(estudiante.getUsmoId()));
    matriculaMoodle.setRomoId(5L);
    matriculaMoodle.setMamoFechaCambio(fechaCambio);
    matriculaMoodle.setMamoRegistradoPor("ws");

    MatriculaMoodle guardar = imatriculaMoodleDataProviders.save(matriculaMoodle);
    logResultadoAlmacenamiento(guardar);
  }

  private void eliminarMatricula(EstudiantesMatriculaMoodleVo estudiante) throws Exception {
    MatriculaMoodle matriculaMoodle = new MatriculaMoodle();
    matriculaMoodle.setMamoId(Long.valueOf(estudiante.getMamoId()));
    matriculaMoodle.setGrseId(Long.valueOf(estudiante.getGrseId()));
    matriculaMoodle.setUsmoId(Long.valueOf(estudiante.getUsmoId()));
    matriculaMoodle.setRomoId(Long.valueOf(estudiante.getRomoId()));
    imatriculaMoodleDataProviders.delete(matriculaMoodle);
  }


  private void logResultadoAlmacenamiento(MatriculaMoodle guardar) {
    if (guardar != null) {
      log.info("Almacenamiento en base de datos con éxito");
    } else {
      log.info("Error al almacenar en la base de datos");
    }
  }

  /**
   * Unenrolls students from Moodle.
   *
   * <p>
   * This method retrieves a list of students to be unenrolled from Moodle and processes each one.
   * For each student, it creates a `MatriculaMoodlewsVo`
   * object and sends an unenrollment request to Moodle
   * via the `matriculaMoodleRestTemplate`. If the unenrollment is successful,
   * it removes the student's
   * enrollment record from the local database.
   * </p>
   *
   * @throws Exception if there is an error during the unenrollment process
   */
  public void desmatriculaMoodle() throws Exception {
    List<EstudiantesMatriculaMoodleVo> estudiantesMatriculaMoodleVos =
        imatriculaMoodleDataProviders.listarDesmatricula(
            "1", null, null, null, null);
    for (EstudiantesMatriculaMoodleVo estudiante : estudiantesMatriculaMoodleVos) {
      MatriculaMoodlewsVo matriculaMoodlewsVo = crearMatriculaMoodleVo(estudiante);
      RespuestaMatriculaMoodleVo respuesta = matriculaMoodleRestTemplate.desMatriculaMoodle(
          conexion.conexionPregradoDesMatriculaMoodle(), matriculaMoodlewsVo);
      if (respuesta.isEjecucion()) {
        eliminarMatricula(estudiante);
      }
    }


  }

}
