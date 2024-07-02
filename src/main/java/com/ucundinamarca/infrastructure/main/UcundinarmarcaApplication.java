package com.ucundinamarca.infrastructure.main;

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
  "com.ucundinamarca.infrastructure",
  "com.ucundinamarca.infrastructure.configuration",
  "com.ucundinamarca.modules",
  "com.ucundinamarca.crosscutting"})
@EnableJpaRepositories(basePackages = {
  "com.ucundinamarca.crosscutting.persistence.repository"})
@EntityScan(basePackages = "com.ucundinamarca.crosscutting.persistence.entity")
public class UcundinarmarcaApplication {

  public static void main(String[] args) {
    SpringApplication.run(UcundinarmarcaApplication.class, args);
  }

}
