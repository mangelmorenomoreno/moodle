package com.prueba.carvajal.infrastructure.configuration;


import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring boot configuration for Datasource runtime.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-06
 */

@Configuration
public class DatasourceConfiguration {

  public static final String JPA_DATASOURCE = "datasource";

  @Bean(name = JPA_DATASOURCE)
  @ConfigurationProperties(prefix = "spring.datasource.carvajal")
  public DataSource dataSourceEbusiness() {
    return DataSourceBuilder.create().build();
  }

}
