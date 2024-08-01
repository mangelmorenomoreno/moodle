package com.ucundinamarca.infrastructure.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase de configuración para main en Spring Boot.
 *
 * @author miguel.moreno
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
