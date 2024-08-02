package com.ucundinamarca.infrastructure.configuration;

import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * Spring boot configuration for Campos de Aprendizaje Udec Datasource runtime.
 * This configuration class sets up the DataSource, EntityManagerFactory,
 * and TransactionManager for the Campos de Aprendizaje Udec database.
 *
 * @version 1.0
 * @since 2024-03-07
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.repository",
    entityManagerFactoryRef = "camposAprendizajeUdecEntityManagerFactory",
    transactionManagerRef = "camposAprendizajeUdecTransactionManager"
)
public class CamposAprendizajeUdecDataSourceConfig {

  /**
   * Creates and configures the DataSource for Campos de Aprendizaje Udec.
   *
   * @return the configured DataSource
   */
  @Bean(name = "camposAprendizajeUdecDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.camposaprendizajeudec")
  public DataSource camposAprendizajeUdecDataSource() {

    return DataSourceBuilder.create().build();
  }

  /**
   * Creates and configures the EntityManagerFactory for Campos de Aprendizaje Udec.
   *
   * @param builder the EntityManagerFactoryBuilder
   * @param dataSource the DataSource for Campos de Aprendizaje Udec
   * @return the configured LocalContainerEntityManagerFactoryBean
   */
  @Bean(name = "camposAprendizajeUdecEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean camposAprendizajeUdecEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("camposAprendizajeUdecDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages(
            "com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity")
        .persistenceUnit("camposAprendizajeUdec")
        .properties(hibernateProperties())
        .build();
  }

  /**
   * Creates and configures the TransactionManager for Campos de Aprendizaje Udec.
   *
   * @param entityManagerFactory the EntityManagerFactory for Campos de Aprendizaje Udec
   * @return the configured PlatformTransactionManager
   */
  @Bean(name = "camposAprendizajeUdecTransactionManager")
  public PlatformTransactionManager camposAprendizajeUdecTransactionManager(
      @Qualifier("camposAprendizajeUdecEntityManagerFactory")
      EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  /**
   * Creates the EntityManagerFactoryBuilder bean.
   *
   * @return the configured EntityManagerFactoryBuilder
   */
  @Bean
  public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
    return new EntityManagerFactoryBuilder(
        new HibernateJpaVendorAdapter(),
        hibernateProperties(),
        null
    );
  }

  /**
   * Configures the Hibernate properties.
   *
   * @return a map of Hibernate properties
   */
  private Map<String, Object> hibernateProperties() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
    return properties;
  }
}
