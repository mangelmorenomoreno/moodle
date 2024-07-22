package com.ucundinamarca.infrastructure.configuration;

import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateConfig.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Configuration
public class RestTemplateConfig {

  /**
   * Configura un bean de RestTemplate que confía en todos los certificados SSL.
   *
   * @return una instancia configurada de RestTemplate
   * @throws Exception si ocurre un error durante la configuración de SSL
   */
  @Bean
  public RestTemplate restTemplate() throws Exception {

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
    SSLContext sslContext = SSLContextBuilder.create()
        .loadTrustMaterial((chain, authType) -> true)
        .build();
    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);

    HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder
        .create().setSSLSocketFactory(socketFactory).build();
    CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
        .build();
    HttpComponentsClientHttpRequestFactory factory =
        new HttpComponentsClientHttpRequestFactory(httpClient);
    return new RestTemplate(factory);
  }

}
