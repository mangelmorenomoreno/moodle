package com.ucundinamarca.modules.usuariomoodle.resttemplate;

import com.ucundinamarca.crosscutting.domain.dto.autentication.ConexionVo;
import com.ucundinamarca.crosscutting.domain.dto.estudiantes.RespuestaEstudianteVo;
import com.ucundinamarca.crosscutting.domain.dto.estudiantes.UsuariowsVo;
import jakarta.annotation.PostConstruct;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
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
 * UsuarioMoodleRestTemplate.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Service
@Log4j2
public class UsuarioMoodleRestTemplate {

  @Autowired
  private RestTemplate restTemplate;

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
   * Creates a teacher in Moodle.
   *
   * @param conexionVo the connection details.
   * @param usuarioVo  the user details.
   * @return the response from the Moodle service.
   */
  public RespuestaEstudianteVo crearDocente(ConexionVo conexionVo, UsuariowsVo usuarioVo) {
    RespuestaEstudianteVo respuestaEstudianteVo = new RespuestaEstudianteVo();
    try {
      String responseStr = null;
      String urlParameters = "&users[0][email]=" + usuarioVo.getEmail() + "&users[0][lastname]="
          + usuarioVo.getLastname() + "&users[0][firstname]=" + usuarioVo.getFirstname()
          + "&users[0][password]=" + usuarioVo.getPasword()
          + "&users[0][username]=" + usuarioVo.getUsername()
          + "&users[0][auth]=" + "&users[0][customfields][0][type]=identificacion"
          + "&users[0][customfields][0][value]=" + usuarioVo.getIdentificacion();
      log.info(urlParameters);
      String serverUrl = conexionVo.getUrl() + "?wstoken=" + conexionVo.getWstoken()
          + "&moodlewsrestformat=" + conexionVo.getMoodlewsrestformat()
          + "&wsfunction=" + conexionVo.getWsfunction();
      responseStr = restTemplate.postForObject(serverUrl, urlParameters, String.class);
      if (responseStr != null && !responseStr.isEmpty()) {
        JSONObject jsonDates;
        if (responseStr.charAt(0) == '[') {
          JSONArray jsonResult = new JSONArray(responseStr);
          for (int i = 0; i < jsonResult.length(); i++) {
            jsonDates = jsonResult.getJSONObject(i);
            respuestaEstudianteVo.setUsername(jsonDates.getString("username"));
            respuestaEstudianteVo.setId(jsonDates.getString("id"));
            respuestaEstudianteVo.setException(null);
            respuestaEstudianteVo.setErrorcode(null);
            respuestaEstudianteVo.setMessage(null);
          }
        } else if (responseStr.charAt(0) == '{') {
          jsonDates = new JSONObject(responseStr);
          respuestaEstudianteVo.setUsername(null);
          respuestaEstudianteVo.setId(null);
          respuestaEstudianteVo.setException(jsonDates.getString("exception"));
          respuestaEstudianteVo.setErrorcode(jsonDates.getString("errorcode"));
          respuestaEstudianteVo.setMessage(jsonDates.getString("message"));
        }
      }
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      e.printStackTrace();
      log.error(UsuarioMoodleRestTemplate.class.getName(), e);
    } catch (JSONException e) {
      e.printStackTrace();
      log.info(UsuarioMoodleRestTemplate.class.getName(), e);
    }
    return respuestaEstudianteVo;
  }

  /**
   * Creates a user in Moodle.
   *
   * @param conexionVo the connection details.
   * @param usuarioVo  the user details.
   * @return the response from the Moodle service.
   */
  public RespuestaEstudianteVo crearEstudiante(ConexionVo conexionVo, UsuariowsVo usuarioVo) {
    RespuestaEstudianteVo respuestaEstudianteVo = new RespuestaEstudianteVo();
    try {
      String urlParameters = "&users[0][email]=" + usuarioVo.getEmail()
          + "&users[0][lastname]=" + usuarioVo.getLastname()
          + "&users[0][firstname]=" + usuarioVo.getFirstname()
          + "&users[0][password]=" + usuarioVo.getPasword()
          + "&users[0][username]=" + usuarioVo.getUsername()
          + "&users[0][customfields][0][type]=programa"
          + "&users[0][customfields][0][value]=" + usuarioVo.getPrograma()
          + "&users[0][customfields][1][type]=sede"
          + "&users[0][customfields][1][value]=" + usuarioVo.getSede()
          + "&users[0][customfields][2][type]=identificacion"
          + "&users[0][customfields][2][value]=" + usuarioVo.getIdentificacion()
          + "&users[0][customfields][3][type]=tipo_usuario"
          + "&users[0][customfields][3][value]=" + usuarioVo.getTipo();
      if (usuarioVo.getCodigoEstudiante() != null && !usuarioVo.getCodigoEstudiante().equals("0")) {
        urlParameters += "&users[0][customfields][4][type]=codigo_estudiante"
            + "&users[0][customfields][4][value]=" + usuarioVo.getCodigoEstudiante();
      } else {
        urlParameters += "&users[0][customfields][4][type]=codigo_estudiante"
            + "&users[0][customfields][4][value]=" + usuarioVo.getIdentificacion();
      }
      if (usuarioVo.getFacultad() != null) {
        urlParameters += "&users[0][customfields][5][type]=facultad"
            + "&users[0][customfields][5][value]=" + usuarioVo.getFacultad();
      }
      log.info(urlParameters);
      String serverUrl = conexionVo.getUrl()
          + "?wstoken=" + conexionVo.getWstoken()
          + "&moodlewsrestformat=" + conexionVo.getMoodlewsrestformat()
          + "&wsfunction=" + conexionVo.getWsfunction();
      log.info(serverUrl);
      String responseStr = restTemplate.postForObject(serverUrl, urlParameters, String.class);
      if (responseStr != null && !responseStr.isEmpty()) {
        JSONObject jsonDates;
        if (responseStr.charAt(0) == '[') {
          JSONArray jsonResult = new JSONArray(responseStr);
          for (int i = 0; i < jsonResult.length(); i++) {
            jsonDates = jsonResult.getJSONObject(i);
            respuestaEstudianteVo.setUsername(jsonDates.getString("username"));
            respuestaEstudianteVo.setId(jsonDates.getString("id"));
            respuestaEstudianteVo.setException(null);
            respuestaEstudianteVo.setErrorcode(null);
            respuestaEstudianteVo.setMessage(null);
          }
        } else if (responseStr.charAt(0) == '{') {
          jsonDates = new JSONObject(responseStr);
          respuestaEstudianteVo.setUsername(null);
          respuestaEstudianteVo.setId(null);
          respuestaEstudianteVo.setException(jsonDates.getString("exception"));
          respuestaEstudianteVo.setErrorcode(jsonDates.getString("errorcode"));
          respuestaEstudianteVo.setMessage(jsonDates.getString("message"));
        }
      }
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      e.printStackTrace();
      log.error(UsuarioMoodleRestTemplate.class.getName(), e);
    } catch (JSONException e) {
      e.printStackTrace();
      log.info(UsuarioMoodleRestTemplate.class.getName(), e);
    }
    return respuestaEstudianteVo;
  }

}
