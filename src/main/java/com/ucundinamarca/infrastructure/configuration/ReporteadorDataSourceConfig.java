package com.ucundinamarca.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.reporteador",
        entityManagerFactoryRef = "reporteadorEntityManagerFactory",
        transactionManagerRef = "reporteadorTransactionManager"
)
public class ReporteadorDataSourceConfig {

    @Bean(name = "reporteadorDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.reporteador")
    public DataSource reporteadorDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "reporteadorEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean reporteadorEntityManagerFactory(
            @Qualifier("reporteadorDataSource") DataSource reporteadorDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(reporteadorDataSource);
        em.setPackagesToScan("com.example.reporteador");  // Cambia el paquete a donde están tus entidades de reporteador

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(new HashMap<String, Object>() {{
            put("hibernate.hbm2ddl.auto", "none");
            put("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
        }});

        return em;
    }

    @Bean(name = "reporteadorTransactionManager")
    public PlatformTransactionManager reporteadorTransactionManager(
            @Qualifier("reporteadorEntityManagerFactory") LocalContainerEntityManagerFactoryBean reporteadorEntityManagerFactory) {
        return new JpaTransactionManager(reporteadorEntityManagerFactory.getObject());
    }
}

