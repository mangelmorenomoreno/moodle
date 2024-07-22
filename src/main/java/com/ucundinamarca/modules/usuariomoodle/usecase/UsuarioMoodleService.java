package com.ucundinamarca.modules.usuariomoodle.usecase;

import com.ucundinamarca.crosscutting.domain.dto.autentication.ConexionVo;
import com.ucundinamarca.crosscutting.domain.dto.estudiantes.RespuestaEstudianteVo;
import com.ucundinamarca.crosscutting.domain.dto.estudiantes.UsuariowsVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.EstudiantesVo;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.UsuarioMoodle;
import com.ucundinamarca.crosscutting.utils.CaracteresEspeciales;
import com.ucundinamarca.crosscutting.utils.Conexion;
import com.ucundinamarca.modules.reporteador.dataproviders.IreporteadorDataProviders;
import com.ucundinamarca.modules.usuariomoodle.dataproviders.IusuarioMoodleDataProviders;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
  private RestTemplate restTemplate;

  private final Lock lock = new ReentrantLock();

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
   * Método que se ejecuta cada segundo para la creación de usuarios.
   */
  @Scheduled(cron = "*/1 * * * * ?")
  public void ejecutarCreacionUsuario() {
    if (lock.tryLock()) {
      try {
        log.info("inicia ejecutarCreacionUsuario " + System.currentTimeMillis());
        procesarEstudiantes();
        log.info("finaliza ejecutarCreacionUsuario:" + System.currentTimeMillis());
      } catch (Exception e) {
        log.error("Error en ejecutarCreacionUsuario", e);
      } finally {
        lock.unlock();
      }
    } else {
      log.info("Cron ya está en ejecución, omitiendo ejecución...");
    }
  }

  private void procesarEstudiantes() throws UnsupportedEncodingException {
    List<EstudiantesVo> estudiantes = iusuarioMoodleDataProviders.listarEstudianteSin("1",
        "252565", iusuarioMoodleDataProviders.listPeriodoUniversidad().getPeunId(),
        null,
        null, null, null, null, null, null, null);
    procesarEstudiantes(estudiantes);
  }

  /**
   * Procesa la lista de estudiantes para su creación en Moodle.
   *
   * @param estudiantes Lista de estudiantes a procesar.
   * @throws UnsupportedEncodingException Si ocurre un error de codificación.
   */
  public void procesarEstudiantes(List<EstudiantesVo> estudiantes)
      throws UnsupportedEncodingException {
    if (estudiantes != null) {
      log.info("Cantidad de estudiantes " + estudiantes.size());
      int[] counters = {0, 0, 0, 0};
      Timestamp fechacambio = new Timestamp(System.currentTimeMillis());
      for (EstudiantesVo estudiante : estudiantes) {
        UsuariowsVo usuariowsVo = mapEstudianteToUsuarioVo(estudiante);
        RespuestaEstudianteVo respuesta = crearUsuario(
            conexion.conexionPregradoCrearEstudiante(2), usuariowsVo);
        processResponse(estudiante, respuesta, counters, fechacambio);
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
        + "20241a" + ultimosDosDigitos));
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

  private String encodeString(String value) throws UnsupportedEncodingException {
    return URLEncoder.encode(value, "UTF-8");
  }

  private void processResponse(EstudiantesVo estudiante, RespuestaEstudianteVo respuesta,
                               int[] counters, Timestamp fechacambio) {
    if (respuesta.getId() != null) {
      log.info("Ejecutó bien el web service");
      counters[2]++;
      UsuarioMoodle usuarioMoodle = createUsuarioMoodle(estudiante, respuesta, fechacambio);
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

  private UsuarioMoodle createUsuarioMoodle(
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

  private void logCounters(int[] counters) {
    log.info("Cantidad de usuario registrados en la base de datos: " + counters[1]
        + ", Cantidad de usuarios registrados en Moodle: " + counters[2]
        + ", Cantidad de usuarios con error: " + counters[0]
        + ", cantidad de errores de Moodle: " + counters[3]);
  }

  /**
   * Crea un usuario en Moodle.
   *
   * @param conexionVo Objeto de conexión configurado para el servicio web.
   * @param usuarioVo  Objeto que contiene la información del usuario a crear.
   * @return Un objeto {@link RespuestaEstudianteVo} con la respuesta del servicio web.
   */
  public RespuestaEstudianteVo crearUsuario(ConexionVo conexionVo, UsuariowsVo usuarioVo) {
    RespuestaEstudianteVo respuestaEstudianteVo = new RespuestaEstudianteVo();
    try {
      String urlParameters = getUrlParameters(usuarioVo);
      String serverUrl = getServerUrl(conexionVo);
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/x-www-form-urlencoded");
      HttpEntity<String> entity = new HttpEntity<>(urlParameters, headers);
      ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, entity,
          String.class);
      parseResponse(response.getBody(), respuestaEstudianteVo);
    } catch (RestClientException | JSONException | UnsupportedEncodingException ex) {
      log.error(UsuarioMoodleService.class.getName(), ex);
    }
    return respuestaEstudianteVo;
  }

  private String getUrlParameters(UsuariowsVo usuarioVo) throws UnsupportedEncodingException {
    return String.format(
        "&users[0][email]=%s&users[0][lastname]=%s&users[0]"
            + "[firstname]=%s&users[0][password]=%s"
            + "&users[0][username]=%s&users[0][customfields]"
            + "[0][type]=programa&users[0][customfields]"
            + "[0][value]=%s&users[0][customfields][1]"
            + "[type]=sede&users[0][customfields][1][value]=%s"
            + "&users[0][customfields][2][type]=identificacion&users[0]"
            + "[customfields][2][value]=%s"
            + "&users[0][customfields][3]"
            + "[type]=codigo_estudiante&users[0][customfields][3][value]=%s"
            + "&users[0][customfields][4]"
            + "[type]=facultad&users[0][customfields][4][value]=%s",
        encodeString(usuarioVo.getEmail()),
        encodeString(usuarioVo.getLastname()),
        encodeString(usuarioVo.getFirstname()),
        encodeString(usuarioVo.getPasword()),
        encodeString(usuarioVo.getUsername()),
        encodeString(usuarioVo.getPrograma()),
        encodeString(usuarioVo.getSede()),
        encodeString(usuarioVo.getIdentificacion()),
        encodeString(usuarioVo.getCodigoEstudiante() != null
            && !usuarioVo.getCodigoEstudiante().equals("0")
            ? usuarioVo.getCodigoEstudiante() : usuarioVo.getIdentificacion()),
        usuarioVo.getFacultad() != null
            ? encodeString(usuarioVo.getFacultad()) : "");
  }

  private String getServerUrl(ConexionVo conexionVo) {
    return UriComponentsBuilder.fromHttpUrl(conexionVo.getUrl())
        .queryParam("wstoken", conexionVo.getWstoken())
        .queryParam("moodlewsrestformat", conexionVo.getMoodlewsrestformat())
        .queryParam("wsfunction", conexionVo.getWsfunction())
        .toUriString();
  }

  private void parseResponse(String response, RespuestaEstudianteVo respuestaEstudianteVo)
      throws JSONException {
    JSONObject jsonDates;
    if (response != null && !response.isEmpty()) {
      if (response.charAt(0) == '[') {
        JSONArray jsonResult = new JSONArray(response);
        for (int i = 0; i < jsonResult.length(); i++) {
          jsonDates = jsonResult.getJSONObject(i);
          respuestaEstudianteVo.setUsername(jsonDates.getString("username"));
          respuestaEstudianteVo.setId(jsonDates.getString("id"));
          respuestaEstudianteVo.setException(null);
          respuestaEstudianteVo.setErrorcode(null);
          respuestaEstudianteVo.setMessage(null);
        }
      } else if (response.charAt(0) == '{') {
        jsonDates = new JSONObject(response);
        respuestaEstudianteVo.setUsername(null);
        respuestaEstudianteVo.setId(null);
        respuestaEstudianteVo.setException(jsonDates.getString("exception"));
        respuestaEstudianteVo.setErrorcode(jsonDates.getString("errorcode"));
        respuestaEstudianteVo.setMessage(jsonDates.getString("message"));
      }
    }
  }
}
