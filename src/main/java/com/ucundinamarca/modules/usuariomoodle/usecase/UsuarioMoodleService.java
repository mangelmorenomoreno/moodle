package com.ucundinamarca.modules.usuariomoodle.usecase;

import com.ucundinamarca.crosscutting.domain.dto.estudiantes.RespuestaEstudianteVo;
import com.ucundinamarca.crosscutting.domain.dto.estudiantes.UsuariowsVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.DocentesVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.EstudiantesVo;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.UsuarioMoodle;
import com.ucundinamarca.crosscutting.utils.CaracteresEspeciales;
import com.ucundinamarca.crosscutting.utils.Conexion;
import com.ucundinamarca.modules.reporteador.dataproviders.IreporteadorDataProviders;
import com.ucundinamarca.modules.usuariomoodle.dataproviders.IusuarioMoodleDataProviders;
import com.ucundinamarca.modules.usuariomoodle.resttemplate.UsuarioMoodleRestTemplate;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UsuarioMoodleService.
 */
@Log4j2
@Service
public class UsuarioMoodleService {

  @Autowired
  private IusuarioMoodleDataProviders iusuarioMoodleDataProviders;

  @Autowired
  private IreporteadorDataProviders ireporteadorDataProviders;

  @Autowired
  private Conexion conexion;

  @Autowired
  private UsuarioMoodleRestTemplate usuarioMoodleRestTemplate;


  /**
   * Obtiene una lista de estudiantes de pregrado que no están matriculados.
   *
   * @return Una lista de objetos {@link EstudiantesVo} que representan estudiantes sin matrícula.
   */
  public List<EstudiantesVo> estudiantesSinMatriculaPregrado() {
    return iusuarioMoodleDataProviders.listarEstudianteSin("1", null,
        iusuarioMoodleDataProviders.listPeriodoUniversidad().getPeunId(),
        null, null, null, null,
        null, null, null, null);
  }


  /**
   * Processes students for enrollment in Moodle.
   *
   * @throws UnsupportedEncodingException if an unsupported encoding is encountered during.
   */
  public void procesarCrearUsuarioEstudiantes() throws UnsupportedEncodingException {
    List<EstudiantesVo> estudiantes = iusuarioMoodleDataProviders.listarEstudianteSin("1",
        "155209", iusuarioMoodleDataProviders.listPeriodoUniversidad().getPeunId(),
        null,
        null, null, null, null, null, null, null);
    procesarCrearUsuarioEstudiantes(estudiantes);
  }

  /**
   * Procesa la lista de estudiantes para su creación en Moodle.
   *
   * @param estudiantes Lista de estudiantes a procesar.
   * @throws UnsupportedEncodingException Si ocurre un error de codificación.
   */
  public void procesarCrearUsuarioEstudiantes(List<EstudiantesVo> estudiantes)
      throws UnsupportedEncodingException {
    if (estudiantes != null) {
      log.info("Cantidad de estudiantes " + estudiantes.size());
      int[] counters = {0, 0, 0, 0};
      Timestamp fechacambio = new Timestamp(System.currentTimeMillis());
      for (EstudiantesVo estudiante : estudiantes) {
        UsuariowsVo usuariowsVo = mapEstudianteToUsuarioVo(estudiante);
        RespuestaEstudianteVo respuesta = usuarioMoodleRestTemplate.crearEstudiante(
            conexion.conexionPregradoCrearEstudiante(), usuariowsVo);
        processResponseEstudiante(estudiante, respuesta, counters, fechacambio);
      }
      logCounters(counters);
    }
  }

  private void procesarCrearUsuarioDocentes(List<DocentesVo> docentesVos)
      throws UnsupportedEncodingException {
    if (docentesVos != null) {
      log.info("Cantidad de docentes " + docentesVos.size());
      int[] counters = {0, 0, 0, 0};
      Timestamp fechacambio = new Timestamp(System.currentTimeMillis());
      for (DocentesVo docentes : docentesVos) {
        UsuariowsVo usuariowsVo = mapDocentesToUsuarioVo(docentes);
        RespuestaEstudianteVo respuesta = usuarioMoodleRestTemplate.crearDocente(
            conexion.conexionPregradoCrearEstudiante(), usuariowsVo);
        processResponseDocente(docentes, respuesta, counters, fechacambio);
      }
      logCounters(counters);
    }
  }

  private UsuariowsVo mapEstudianteToUsuarioVo(EstudiantesVo estudiante)
      throws UnsupportedEncodingException {
    UsuariowsVo usuariowsVo = new UsuariowsVo();
    String codigoEstudiante = estudiante.getCodigo() != null && !"0".equals(estudiante.getCodigo())
        ? estudiante.getCodigo() : estudiante.getPegeDocumentoidentidad();
    usuariowsVo.setCodigoEstudiante(encodeString(CaracteresEspeciales.clearString(
        codigoEstudiante.toUpperCase())));
    usuariowsVo.setEmail(encodeString(estudiante.getPengEmailinstitucional().toLowerCase()));
    usuariowsVo.setFirstname(encodeString(
        CaracteresEspeciales.clearString(estudiante.getPengPrimernombre().toUpperCase())
            + (estudiante.getPengSegundonombre() != null ? " "
            + CaracteresEspeciales.clearString(estudiante.getPengSegundonombre().toUpperCase())
            : "")));
    usuariowsVo.setIdentificacion(encodeString(estudiante.getPegeDocumentoidentidad()));
    usuariowsVo.setLastname(encodeString(
        CaracteresEspeciales.clearString(estudiante.getPengPrimerapellido().toUpperCase())
            + (estudiante.getPengSegundoapellido() != null ? " "
            + CaracteresEspeciales.clearString(estudiante.getPengSegundoapellido().toUpperCase())
            : "")));
    String ultimosDosDigitos = estudiante.getPegeDocumentoidentidad()
        .substring(estudiante.getPegeDocumentoidentidad().length() - 2);
    usuariowsVo.setPasword(encodeString(estudiante.getPengPrimerapellido().toUpperCase()
        + iusuarioMoodleDataProviders.listPeriodoUniversidad().getPeunAno()
        + iusuarioMoodleDataProviders.listPeriodoUniversidad().getPeunPeriodo()
        + "a" + ultimosDosDigitos));
    usuariowsVo.setPrograma(encodeString(
        CaracteresEspeciales.clearString(estudiante.getProgNombre().toUpperCase())));
    usuariowsVo.setSede(encodeString(
        CaracteresEspeciales.clearString(estudiante.getUnidNombre().toUpperCase())));
    usuariowsVo.setUsername(encodeString(
        CaracteresEspeciales.clearString(estudiante.getUsuaUsuario())));
    usuariowsVo.setFacultad(estudiante.getFacultad() != null ? encodeString(
        CaracteresEspeciales.clearString(estudiante.getFacultad().toUpperCase())) : null);
    return usuariowsVo;
  }


  private UsuariowsVo mapDocentesToUsuarioVo(DocentesVo docentesVo)
      throws UnsupportedEncodingException {
    String ultimosDosDigitos = null;
    UsuariowsVo usuariowsVo = new UsuariowsVo();
    usuariowsVo.setEmail(URLEncoder.encode(docentesVo.getPengEmailinstitucional().toLowerCase(),
        "UTF-8"));
    usuariowsVo.setIdentificacion(URLEncoder.encode(docentesVo.getPegeDocumentoidentidad(),
        "UTF-8"));
    ultimosDosDigitos = docentesVo.getPegeDocumentoidentidad().substring(
        docentesVo.getPegeDocumentoidentidad().length() - 2);
    usuariowsVo.setPasword(URLEncoder.encode(docentesVo.getPengPrimerapellido().toUpperCase()
        + iusuarioMoodleDataProviders.listPeriodoUniversidad().getPeunAno()
        + iusuarioMoodleDataProviders.listPeriodoUniversidad().getPeunPeriodo()
        + "a" + ultimosDosDigitos, "UTF-8"));
    usuariowsVo.setUsername(URLEncoder.encode(CaracteresEspeciales.clearString(
        docentesVo.getUsuaUsuario().toLowerCase()), "UTF-8"));
    if (docentesVo.getPengSegundonombre() != null) {
      usuariowsVo.setFirstname(URLEncoder.encode(CaracteresEspeciales.clearString(
              docentesVo.getPengPrimernombre().toUpperCase()) + " "
              + CaracteresEspeciales.clearString(docentesVo.getPengSegundonombre().toUpperCase()),
          "UTF-8"));
    } else {
      usuariowsVo.setFirstname(URLEncoder.encode(CaracteresEspeciales.clearString(
          docentesVo.getPengPrimernombre().toUpperCase()), "UTF-8"));
    }
    if (docentesVo.getPengSegundoapellido() != null) {
      usuariowsVo.setLastname(URLEncoder.encode(CaracteresEspeciales.clearString(
              docentesVo.getPengPrimerapellido().toUpperCase()) + " "
              + CaracteresEspeciales.clearString(docentesVo.getPengSegundoapellido().toUpperCase()),
          "UTF-8"));
    } else {
      usuariowsVo.setLastname(URLEncoder.encode(CaracteresEspeciales.clearString(
          docentesVo.getPengPrimerapellido().toUpperCase()), "UTF-8"));

    }
    return usuariowsVo;
  }

  private String encodeString(String value) throws UnsupportedEncodingException {
    return URLEncoder.encode(value, "UTF-8");
  }

  private void processResponseEstudiante(EstudiantesVo estudiante, RespuestaEstudianteVo respuesta,
                                         int[] counters, Timestamp fechacambio) {
    if (respuesta.getId() != null) {
      log.info("Ejecutó bien el web service");
      counters[2]++;
      UsuarioMoodle usuarioMoodle = createUsuarioEstudianteMoodle(
          estudiante, respuesta, fechacambio);
      if (iusuarioMoodleDataProviders.save(usuarioMoodle) != null) {
        counters[1]++;
        log.info("SUCCESS " + counters[1] + " ID USUARIO " + respuesta.getId());
      } else {
        counters[0]++;
        log.error("ERROR BD " + counters[0]);
      }
    } else {
      counters[3]++;
      log.error("Error " + respuesta.getException() + " " + respuesta.getErrorcode() + " "
          + respuesta.getMessage());
    }
  }

  private void processResponseDocente(DocentesVo docentesVo, RespuestaEstudianteVo respuesta,
                                      int[] counters, Timestamp fechacambio) {
    if (respuesta.getId() != null) {
      log.info("Ejecutó bien el web service");
      counters[2]++;
      UsuarioMoodle usuarioMoodle = createUsuarioDocenteMoodle(docentesVo, respuesta, fechacambio);
      if (iusuarioMoodleDataProviders.save(usuarioMoodle) != null) {
        counters[1]++;
        log.info("SUCCESS " + counters[1] + " ID USUARIO " + respuesta.getId());
      } else {
        counters[0]++;
        log.error("ERROR BD " + counters[0]);
      }
    } else {
      counters[3]++;
      log.error("Error " + respuesta.getException() + " " + respuesta.getErrorcode() + " "
          + respuesta.getMessage());
    }
  }

  private UsuarioMoodle createUsuarioEstudianteMoodle(
      EstudiantesVo estudiante, RespuestaEstudianteVo respuesta, Timestamp fechacambio) {
    UsuarioMoodle usuarioMoodle = new UsuarioMoodle();
    usuarioMoodle.setPegeId(estudiante.getPegeId());
    usuarioMoodle.setEstpId(estudiante.getEstpId());
    usuarioMoodle.setProgId(estudiante.getProgId());
    usuarioMoodle.setTiusId(1);
    usuarioMoodle.setUnidId(estudiante.getUnidId());
    usuarioMoodle.setInstId(estudiante.getNiedId());
    usuarioMoodle.setUsmoUsuario(respuesta.getUsername());
    usuarioMoodle.setUsmoIdMoodle(respuesta.getId());
    usuarioMoodle.setUsmoFechaCambio(fechacambio);
    usuarioMoodle.setUsmoRegistradoPor("wsMoodle");
    return usuarioMoodle;
  }

  private UsuarioMoodle createUsuarioDocenteMoodle(
      DocentesVo docentesVo, RespuestaEstudianteVo respuesta, Timestamp fechacambio) {
    UsuarioMoodle usuarioMoodle = new UsuarioMoodle();
    usuarioMoodle.setPegeId(Integer.valueOf(docentesVo.getPegeId()));
    usuarioMoodle.setEstpId(null);
    usuarioMoodle.setProgId(null);
    usuarioMoodle.setAreaId(null);
    usuarioMoodle.setTiusId(2);
    usuarioMoodle.setUnidId(null);
    usuarioMoodle.setInstId(1);
    usuarioMoodle.setUsmoUsuario(respuesta.getUsername());
    usuarioMoodle.setUsmoIdMoodle(respuesta.getId());
    usuarioMoodle.setUsmoFechaCambio(fechacambio);
    usuarioMoodle.setUsmoRegistradoPor("wsMoodle");
    return usuarioMoodle;
  }

  private void logCounters(int[] counters) {
    log.info("Cantidad de usuario registrados en la base de datos: " + counters[1]
        + ", Cantidad de usuarios registrados en Moodle: " + counters[2]
        + ", Cantidad de usuarios con error: " + counters[0]
        + ", cantidad de errores de Moodle: " + counters[3]);
  }


  /**
   * Processes the creation of teachers in Moodle.
   *
   * <p>
   * This method retrieves a list of teachers based on the provided criteria
   * using the `registroListarDocente` method from `iusuarioMoodleDataProviders`.
   * It then calls the `procesarCrearUsuarioDocentes` method to process the
   * creation of the users in Moodle.
   * </p>
   *
   * @throws Exception if an error occurs during the execution of the method.
   */
  public void procesarCrearDocenteMoodle() throws Exception {

    List<DocentesVo> docentesVos = iusuarioMoodleDataProviders.registroListarDocente(null,
        null, iusuarioMoodleDataProviders.listPeriodoUniversidad().getPeunId(),
        null,
        null, null, null);
    procesarCrearUsuarioDocentes(docentesVos);
  }
}
