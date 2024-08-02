package com.ucundinamarca.modules.matriculamoodle.resttemplate;

import com.ucundinamarca.crosscutting.domain.dto.autentication.ConexionVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.MatriculaMoodlewsVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.RespuestaMatriculaMoodleVo;
import jakarta.annotation.PostConstruct;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * MatriculaMoodleRestTemplate.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Service
@Log4j2
public class MatriculaMoodleRestTemplate {

  @Autowired
  private RestTemplate restTemplate;

  /**
   * Initializes the SSL context to trust all certificates.
   *
   * @throws NoSuchAlgorithmException if the SSL algorithm is not available.
   * @throws KeyManagementException   if there is an error initializing the SSL context.
   */
  @PostConstruct
  private void init() throws NoSuchAlgorithmException, KeyManagementException {
    TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
          public X509Certificate[] getAcceptedIssuers() {
            return null;
          }

          public void checkClientTrusted(X509Certificate[] certs, String authType) {
          }

          public void checkServerTrusted(X509Certificate[] certs, String authType) {
          }
        }
    };

    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
  }

  /**
   * Enrolls a user in a Moodle course.
   *
   * @param conexionVo          the connection details.
   * @param matriculaMoodlewsVo the enrollment details.
   * @return the response from the Moodle service.
   */
  public RespuestaMatriculaMoodleVo matriculaMoodle(
      ConexionVo conexionVo, MatriculaMoodlewsVo matriculaMoodlewsVo) {
    String urlParameters = "&enrolments[0][courseid]=" + matriculaMoodlewsVo.getCourseid()
        + "&enrolments[0][roleid]=" + matriculaMoodlewsVo.getRoleid()
        + "&enrolments[0][userid]=" + matriculaMoodlewsVo.getUserid();
    String serverUrl = conexionVo.getUrl() + "?wstoken=" + conexionVo.getWstoken()
        + "&moodlewsrestformat=" + conexionVo.getMoodlewsrestformat()
        + "&wsfunction=" + conexionVo.getWsfunction() + urlParameters;
    log.info(serverUrl);
    return enviarSolicitud(serverUrl);
  }

  /**
   * Unenrolls a user from a Moodle course.
   *
   * @param conexionVo          the connection details.
   * @param matriculaMoodlewsVo the enrollment details.
   * @return the response from the Moodle service.
   */
  public RespuestaMatriculaMoodleVo desMatriculaMoodle(
      ConexionVo conexionVo, MatriculaMoodlewsVo matriculaMoodlewsVo) {
    String urlParameters = "&enrolments[0][courseid]=" + matriculaMoodlewsVo.getCourseid()
        + "&enrolments[0][roleid]=" + matriculaMoodlewsVo.getRoleid()
        + "&enrolments[0][userid]=" + matriculaMoodlewsVo.getUserid();
    String serverUrl = conexionVo.getUrl() + "?wstoken=" + conexionVo.getWstoken()
        + "&moodlewsrestformat=" + conexionVo.getMoodlewsrestformat()
        + "&wsfunction=" + conexionVo.getWsfunction() + urlParameters;
    return enviarSolicitud(serverUrl);
  }

  /**
   * Lists the groups to clean enrollment in Moodle.
   *
   * @param conexionVo   the connection details.
   * @param grupIdMoodle the Moodle group ID.
   * @return a list of enrollment details.
   */
  public List<MatriculaMoodlewsVo> listarGruposLimpiarMatriculaMoodle(
      ConexionVo conexionVo, String grupIdMoodle) {
    List<MatriculaMoodlewsVo> matriculaMoodlewsVos = new ArrayList<>();
    String urlParameters = "&courseid=" + grupIdMoodle + "&options[0][name]=userfields&options[0]"
        + "[value]='id, roles'";
    String serverUrl = conexionVo.getUrl() + "?wstoken=" + conexionVo.getWstoken()
        + "&moodlewsrestformat=" + conexionVo.getMoodlewsrestformat()
        + "&wsfunction=" + conexionVo.getWsfunction();

    try {
      String response = restTemplate.postForObject(serverUrl, urlParameters, String.class);
      if (response != null && !response.isEmpty()) {
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject estudianteJson = jsonArray.getJSONObject(i);
          JSONArray rolesJson = estudianteJson.getJSONArray("roles");
          JSONObject roleJson = rolesJson.getJSONObject(0);
          MatriculaMoodlewsVo matriculaMoodlewsVo = new MatriculaMoodlewsVo();
          matriculaMoodlewsVo.setCourseid(grupIdMoodle);
          matriculaMoodlewsVo.setUserid(String.valueOf(estudianteJson.getInt("id")));
          matriculaMoodlewsVo.setRoleid(String.valueOf(roleJson.getInt("roleid")));
          matriculaMoodlewsVos.add(matriculaMoodlewsVo);
        }
      }
    } catch (HttpClientErrorException | HttpServerErrorException | JSONException e) {
      e.printStackTrace();
    }
    return matriculaMoodlewsVos;
  }

  /**
   * Sends a request to the Moodle service.
   *
   * @param serverUrl the URL of the Moodle service.
   * @return the response from the Moodle service.
   */
  private RespuestaMatriculaMoodleVo enviarSolicitud(String serverUrl) {
    RespuestaMatriculaMoodleVo respuesta = new RespuestaMatriculaMoodleVo();
    try {
      String response = restTemplate.postForObject(serverUrl, "", String.class);
      JSONObject jsonDates = new JSONObject();
      if (response.charAt(0) == '{') {
        jsonDates = new JSONObject(response);
        respuesta.setEjecucion(false);
        respuesta.setException(jsonDates.get("exception").toString());
        respuesta.setMessage(jsonDates.get("message").toString());
      } else {
        respuesta.setEjecucion(true);
        respuesta.setException(null);
        respuesta.setMessage(null);
      }
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      e.printStackTrace();
      respuesta.setEjecucion(false);
      respuesta.setException(e.getMessage());
    } catch (JSONException e) {
      e.printStackTrace();
      respuesta.setEjecucion(false);
      respuesta.setException(e.getMessage());
    }
    return respuesta;
  }
}
