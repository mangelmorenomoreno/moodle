package com.ucundinamarca.modules.gruposemilla.resttemplate;

import com.ucundinamarca.crosscutting.domain.dto.autentication.ConexionVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.GrupoSemillawsVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.RespuestaGruposSemillaVo;
import jakarta.annotation.PostConstruct;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * GrupoMoodleRestTemplate.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Service
@Log4j2
public class GrupoMoodleRestTemplate {

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
   * Creates a group in Moodle.
   *
   * @param conexionVo       the connection details.
   * @param grupoSemillawsVo the group details.
   * @return the response from the Moodle service.
   */
  public RespuestaGruposSemillaVo crearGrupos(
      ConexionVo conexionVo,
      GrupoSemillawsVo grupoSemillawsVo) {
    RespuestaGruposSemillaVo respuestaGruposSemillaVo = new RespuestaGruposSemillaVo();
    String responseStr = null;
    try {
      String urlParameters = "&categoryid=" + grupoSemillawsVo.getCateId()
          + "&courseid=" + grupoSemillawsVo.getCourseid()
          + "&options[0][value]=0"
          + "&options[0][name]=users"
          + "&shortname=" + grupoSemillawsVo.getShortname()
          + "&fullname=" + grupoSemillawsVo.getFullname();
      System.out.println(urlParameters);

      String serverUrl = conexionVo.getUrl()
          + "?wstoken=" + conexionVo.getWstoken()
          + "&moodlewsrestformat=" + conexionVo.getMoodlewsrestformat()
          + "&wsfunction=" + conexionVo.getWsfunction();
      System.out.println(serverUrl);

      responseStr = restTemplate.postForObject(serverUrl, urlParameters, String.class);

      if (responseStr != null) {
        System.out.println("Response: " + responseStr);
        if (responseStr.charAt(0) == '{') {
          JSONObject jsonObject = new JSONObject(responseStr);
          if (jsonObject.has("exception")) {
            respuestaGruposSemillaVo.setException(jsonObject.getString("exception"));
            respuestaGruposSemillaVo.setErrorcode(jsonObject.getString("errorcode"));
            respuestaGruposSemillaVo.setMessage(jsonObject.getString("message"));
            respuestaGruposSemillaVo.setShortname(null);
            respuestaGruposSemillaVo.setId(null);
          }
          if (jsonObject.has("id")) {
            respuestaGruposSemillaVo.setShortname(jsonObject.getString("shortname"));
            respuestaGruposSemillaVo.setId(jsonObject.getString("id"));
            respuestaGruposSemillaVo.setException(null);
            respuestaGruposSemillaVo.setErrorcode(null);
            respuestaGruposSemillaVo.setMessage(null);
          }
        }
      }
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      e.printStackTrace();
      log.error(GrupoMoodleRestTemplate.class.getName(), e);
    } catch (JSONException e) {
      e.printStackTrace();
      log.info(GrupoMoodleRestTemplate.class.getName(), e);
    }
    return respuestaGruposSemillaVo;
  }

}
