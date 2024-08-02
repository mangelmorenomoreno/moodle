package com.ucundinamarca.modules.usuariomoodle.cron;

import com.ucundinamarca.modules.usuariomoodle.usecase.UsuarioMoodleService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * CronUsuarioMoodle.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Service
@Log4j2
public class CronUsuarioMoodle {

  private final Lock lock = new ReentrantLock();


  @Autowired
  private UsuarioMoodleService usuarioMoodleService;

  /**
   * Método que se ejecuta cada segundo para la creación de estudiantes.
   */
  @Scheduled(cron = "*/1 * * * * ?")
  public void ejecutarCreacionEstudiantes() {
    if (lock.tryLock()) {
      try {
        log.info("inicia ejecutarCreacionUsuario " + System.currentTimeMillis());
        usuarioMoodleService.procesarCrearUsuarioEstudiantes();
        log.info("finaliza ejecutarCreacionUsuario:" + System.currentTimeMillis());
      } catch (Exception e) {
        log.error("Error en ejecutarCreacionUsuario", e);
      } finally {
        lock.unlock();
      }
    } else {
      log.info("Cron ya está en ejecución, omitiendo ejecución...");
    }
  }

  /**
   * Método que se ejecuta cada segundo para la creación de docentes.
   */
  //@Scheduled(cron = "*/1 * * * * ?")
  public void ejecutarCreacionDocentes() {
    if (lock.tryLock()) {
      try {
        log.info("inicia ejecutarCreacionUsuario " + System.currentTimeMillis());
        usuarioMoodleService.procesarCrearDocenteMoodle();
        log.info("finaliza ejecutarCreacionUsuario:" + System.currentTimeMillis());
      } catch (Exception e) {
        log.error("Error en ejecutarCreacionUsuario", e);
      } finally {
        lock.unlock();
      }
    } else {
      log.info("Cron ya está en ejecución, omitiendo ejecución...");
    }
  }

}
