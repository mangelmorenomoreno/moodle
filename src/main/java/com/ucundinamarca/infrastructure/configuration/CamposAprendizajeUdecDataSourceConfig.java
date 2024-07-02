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
        basePackages = "com.example.camposAprendizajeUdec",
        entityManagerFactoryRef = "camposAprendizajeUdecEntityManagerFactory",
        transactionManagerRef = "camposAprendizajeUdecTransactionManager"
)
public class CamposAprendizajeUdecDataSourceConfig {

    @Bean(name = "camposAprendizajeUdecDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.camposAprendizajeUdec")
    public DataSource camposAprendizajeUdecDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "camposAprendizajeUdecEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean camposAprendizajeUdecEntityManagerFactory(
            @Qualifier("camposAprendizajeUdecDataSource") DataSource camposAprendizajeUdecDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(camposAprendizajeUdecDataSource);
        em.setPackagesToScan("com.example.camposAprendizajeUdec");  // Cambia el paquete a donde están tus entidades de camposAprendizajeUdec

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(new HashMap<String, Object>() {{
            put("hibernate.hbm2ddl.auto", "none");
            put("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
        }});

        return em;
    }

    @Bean(name = "camposAprendizajeUdecTransactionManager")
    public PlatformTransactionManager camposAprendizajeUdecTransactionManager(
            @Qualifier("camposAprendizajeUdecEntityManagerFactory") LocalContainerEntityManagerFactoryBean camposAprendizajeUdecEntityManagerFactory) {
        return new JpaTransactionManager(camposAprendizajeUdecEntityManagerFactory.getObject());
    }
}
