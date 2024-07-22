package com.ucundinamarca.infrastructure.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase de configuración para main en Spring Boot.
 *
 * @author carvajal
 * @version 1.0
 * @since 2020-04-21
 */
@SpringBootApplication(scanBasePackages = {"com.ucundinamarca"})
@EnableScheduling
public class UcundinarmarcaApplication {

  public static void main(String[] args) {

    SpringApplication.run(UcundinarmarcaApplication.class, args);
  }

}
