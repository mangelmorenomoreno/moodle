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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Spring boot configuration for Datasource runtime.
 * This configuration class sets up the DataSource, EntityManagerFactory,
 * and TransactionManager for the Reporteador database.
 * It also configures the JdbcTemplate for database operations.
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.ucundinamarca.crosscutting.persistence.reporteador.repository",
    entityManagerFactoryRef = "reporteadorEntityManagerFactory",
    transactionManagerRef = "reporteadorTransactionManager"
)
public class ReporteadorDataSourceConfig {

  /**
   * Creates and configures the DataSource for Reporteador.
   *
   * @return the configured DataSource
   */
  @Bean(name = "reporteadorDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.reporteador")
  public DataSource reporteadorDataSource() {
    return DataSourceBuilder.create().build();
  }

  /**
   * Creates and configures the JdbcTemplate for Reporteador.
   *
   * @param dataSource the DataSource for Reporteador
   * @return the configured JdbcTemplate
   */
  @Bean(name = "reporteadorJdbcTemplate")
  public JdbcTemplate reporteadorJdbcTemplate(
      @Qualifier("reporteadorDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  /**
   * Creates and configures the EntityManagerFactory for Reporteador.
   *
   * @param builder the EntityManagerFactoryBuilder
   * @param dataSource the DataSource for Reporteador
   * @return the configured LocalContainerEntityManagerFactoryBean
   */
  @Bean(name = "reporteadorEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean reporteadorEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("reporteadorDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.ucundinamarca.crosscutting.persistence.reporteador.entity")
        .persistenceUnit("reporteador")
        .properties(hibernateProperties())
        .build();
  }

  /**
   * Creates and configures the TransactionManager for Reporteador.
   *
   * @param entityManagerFactory the EntityManagerFactory for Reporteador
   * @return the configured PlatformTransactionManager
   */
  @Bean(name = "reporteadorTransactionManager")
  public PlatformTransactionManager reporteadorTransactionManager(
      @Qualifier("reporteadorEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
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
