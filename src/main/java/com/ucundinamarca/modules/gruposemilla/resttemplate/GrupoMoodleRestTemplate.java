package com.ucundinamarca.modules.gruposemilla.resttemplate;

import com.ucundinamarca.crosscutting.domain.dto.autentication.ConexionVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.GrupoSemillawsVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.RespuestaGruposSemillaVo;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

  private static final TrustManager[] trustAllCerts = new TrustManager[]{
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
      ConexionVo conexionVo, GrupoSemillawsVo grupoSemillawsVo) throws IOException {
    RespuestaGruposSemillaVo respuestaGruposSemillaVo = new RespuestaGruposSemillaVo();
    String responser = null;
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

      HttpsURLConnection con = (HttpsURLConnection) new URL(serverUrl).openConnection();

      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, trustAllCerts, new java.security.SecureRandom());
      con.setDefaultSSLSocketFactory(sc.getSocketFactory());

      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      con.setRequestProperty("Content-Language", "en-US");
      con.setDoOutput(true);
      con.setUseCaches(false);
      con.setDoInput(true);

      DataOutputStream wr = new DataOutputStream(con.getOutputStream());
      wr.writeBytes(urlParameters);
      wr.flush();
      wr.close();

      InputStream is = con.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
      String line;
      StringBuilder response = new StringBuilder();
      while ((line = rd.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      rd.close();

      responser = response.toString();
      System.out.println("response " + responser);

      if (responser != null) {
        if (responser.charAt(0) == '{') {
          JSONObject jsonObject = new JSONObject(responser);
          if (jsonObject.has("exception")) {
            respuestaGruposSemillaVo.setException(jsonObject.getString("exception"));
            respuestaGruposSemillaVo.setErrorcode(jsonObject.getString("errorcode"));
            respuestaGruposSemillaVo.setMessage(jsonObject.getString("message"));
            respuestaGruposSemillaVo.setShortname(null);
            respuestaGruposSemillaVo.setId(null);
          }
          if (jsonObject.has("id")) {
            respuestaGruposSemillaVo.setShortname(jsonObject.getString("shortname"));
            respuestaGruposSemillaVo.setId(String.valueOf(jsonObject.getInt("id")));
            respuestaGruposSemillaVo.setException(null);
            respuestaGruposSemillaVo.setErrorcode(null);
            respuestaGruposSemillaVo.setMessage(null);
          }
        }
      }
    } catch (IOException | JSONException | NoSuchAlgorithmException | KeyManagementException ex) {
      log.info(GrupoMoodleRestTemplate.class.getName(), ex);
      System.out.println("error " + ex);
    }
    return respuestaGruposSemillaVo;
  }
}
