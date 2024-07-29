package com.ucundinamarca.modules.matriculamoodle.cron;

import com.ucundinamarca.modules.matriculamoodle.usecase.MatriculaMoodleService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


/**
 * CronMatriculaMoodle.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Service
@Log4j2
public class CronMatriculaMoodle {

  @Autowired
  private MatriculaMoodleService matriculaMoodleService;

  private final Lock lock = new ReentrantLock();

  /**
   * Scheduled task to enroll students in Moodle.
   * <p>
   * This method is executed every second. It attempts to acquire a lock to ensure
   * that only one instance of the task is running at a time. If the lock is acquired,
   * it invokes the matriculation service to enroll students. Logs the start and end time
   * of the execution, and handles any exceptions that occur during the process.
   * </p>
   */
  @Scheduled(cron = "*/1 * * * * ?")
  public void ejecutarMatriculaEstudiante() {
    if (lock.tryLock()) {
      try {
        log.info("Inicia ejecutarMatriculaEstudiante: " + System.currentTimeMillis());
        matriculaMoodleService.matricularEstudianteMoodle();
        log.info("Finaliza ejecutarMatriculaEstudiante: " + System.currentTimeMillis());
      } catch (Exception e) {
        log.error("Error en ejecutarMatriculaEstudiante", e);
      } finally {
        lock.unlock();
      }
    } else {
      log.info("Cron ya está en ejecución ejecutarMatriculaEstudiante, omitiendo ejecución...");
    }
  }

  /**
   * Scheduled task to unenroll students from Moodle.
   * <p>
   * This method is executed every second. It attempts to acquire a lock to ensure
   * that only one instance of the task is running at a time. If the lock is acquired,
   * it invokes the unenrollment service to unenroll students. Logs the start and end time
   * of the execution, and handles any exceptions that occur during the process.
   * </p>
   */
  @Scheduled(cron = "*/1 * * * * ?")
  public void ejecutarDesMatriculaEstudiante() {
    if (lock.tryLock()) {
      try {
        log.info("Inicia desmatriculaMoodle: " + System.currentTimeMillis());
        matriculaMoodleService.desmatriculaMoodle();
        log.info("Finaliza ejecutarMatriculaEstudiante: " + System.currentTimeMillis());
      } catch (Exception e) {
        log.error("Error en desmatriculaMoodle", e);
      } finally {
        lock.unlock();
      }
    } else {
      log.info("Cron ya está en ejecución desmatriculaMoodle, omitiendo ejecución...");
    }
  }


}
