package com.prueba.carvajal.infrastructure.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Clase de configuración para main en Spring Boot.
 *
 * @author carvajal
 * @version 1.0
 * @since 2020-04-21
 */
@SpringBootApplication(scanBasePackages = {
  "com.prueba.carvajal.infrastructure",
  "com.prueba.carvajal.infrastructure.configuration",
  "com.prueba.carvajal.modules",
  "com.prueba.carvajal.crosscutting"})
@EnableJpaRepositories(basePackages = {
  "com.prueba.carvajal.crosscutting.persistence.repository"})
@EntityScan(basePackages = "com.prueba.carvajal.crosscutting.persistence.entity")
public class CarvajalApplication {

  public static void main(String[] args) {
    SpringApplication.run(CarvajalApplication.class, args);
  }

}
