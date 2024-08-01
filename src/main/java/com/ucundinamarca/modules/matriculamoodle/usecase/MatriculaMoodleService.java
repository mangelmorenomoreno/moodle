package com.ucundinamarca.modules.matriculamoodle.usecase;

import com.ucundinamarca.crosscutting.domain.dto.moodle.DocentesMatriculaVo;
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

  private List<DocentesMatriculaVo> docentesMatriculaVos(
      String grupId, String pegeId, String documento, String peunId,
      String usuario, String programa, String unidad, String instancia) throws Exception {
    return imatriculaMoodleDataProviders.docenteMatricula(grupId, pegeId, documento, peunId,
        usuario, programa, unidad, instancia);
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

  /**
   * Realiza la matriculación de docentes en Moodle.
   *
   * <p>Este método obtiene una lista de docentes a matricular, crea los objetos de matriculación
   * necesarios y realiza la llamada al servicio web de Moodle para completar la matriculación.
   * Si la matriculación es exitosa, guarda la información de la matriculación en la base de datos.
   * </p>
   *
   * <p>El método sigue los siguientes pasos:
   * <ul>
   *   <li>Obtiene la lista de docentes a matricular llamando a <code>docentesMatriculaVos</code>.
   *   </li>
   *   <li>Obtiene la marca de tiempo actual.</li>
   *   <li>Itera sobre cada docente, creando el objeto de matriculación <code>MatriculaMoodlewsVo
   *   </code>.</li>
   *   <li>Llama al servicio web de Moodle utilizando <code>matriculaMoodleRestTemplate</code>
   *       y recibe una respuesta.</li>
   *   <li>Si la matriculación es exitosa, guarda la información de la matriculación llamando a
   *       <code>guardarMatricula</code>.</li>
   * </ul>
   * </p>
   *
   * @throws Exception si ocurre algún error durante el proceso de matriculación.
   * @see #docentesMatriculaVos(String, String, String, String, String, String, String, String)
   * @see #crearMatriculaMoodleVo(DocentesMatriculaVo)
   * @see #guardarMatricula(DocentesMatriculaVo, Timestamp)
   */
  public void matricularDocentesMoodle() throws Exception {
    List<DocentesMatriculaVo> docentesMatriculaVos = docentesMatriculaVos(
        null, null, null, null,
        null, null, null, null);
    Timestamp fechaCambio = new Timestamp(System.currentTimeMillis());
    for (DocentesMatriculaVo docentesMatriculaVo : docentesMatriculaVos) {
      MatriculaMoodlewsVo matriculaMoodlewsVo = crearMatriculaMoodleVo(docentesMatriculaVo);
      RespuestaMatriculaMoodleVo respuesta = matriculaMoodleRestTemplate.matriculaMoodle(
          conexion.conexionPregradoMatriculaMoodle(), matriculaMoodlewsVo);
      if (respuesta.isEjecucion()) {
        guardarMatricula(docentesMatriculaVo, fechaCambio);
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

  private MatriculaMoodlewsVo crearMatriculaMoodleVo(DocentesMatriculaVo datos)
      throws Exception {
    MatriculaMoodlewsVo matriculaMoodlewsVo = new MatriculaMoodlewsVo();
    matriculaMoodlewsVo.setCourseid(URLEncoder.encode(datos.getGrseIdmoodle(),
        StandardCharsets.UTF_8.toString()));
    matriculaMoodlewsVo.setRoleid(URLEncoder.encode(datos.getRomoId(),
        StandardCharsets.UTF_8.toString()));
    matriculaMoodlewsVo.setUserid(URLEncoder.encode(datos.getUsmoId(),
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

  private void guardarMatricula(DocentesMatriculaVo datos, Timestamp fechaCambio) {
    MatriculaMoodle matriculaMoodle = new MatriculaMoodle();
    matriculaMoodle.setGrseId(Long.valueOf(datos.getGrseId()));
    matriculaMoodle.setUsmoId(Long.valueOf(datos.getUsmoId()));
    matriculaMoodle.setRomoId(Long.valueOf(datos.getRomoId()));
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
  public void desmatriculaEstudianteMoodle() throws Exception {
    List<EstudiantesMatriculaMoodleVo> estudiantesMatriculaMoodleVos =
        imatriculaMoodleDataProviders.listarDesmatriculaEstudiantes(
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
