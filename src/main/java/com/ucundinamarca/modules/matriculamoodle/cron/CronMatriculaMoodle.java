package com.ucundinamarca.modules.matriculamoodle.cron;

import com.ucundinamarca.modules.matriculamoodle.usecase.MatriculaMoodleService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
  //@Scheduled(cron = "*/1 * * * * ?")
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
  //@Scheduled(cron = "*/1 * * * * ?")
  public void ejecutarDesMatriculaEstudiante() {
    if (lock.tryLock()) {
      try {
        log.info("Inicia desmatriculaMoodle: " + System.currentTimeMillis());
        matriculaMoodleService.desmatriculaEstudianteMoodle();
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

  /**
   * Ejecuta el proceso de matriculación de docentes en Moodle cada minuto.
   *
   * <p>Este método está programado para ejecutarse cada minuto utilizando una expresión cron.
   * Utiliza un mecanismo de bloqueo para asegurar que solo una instancia del proceso de
   * matriculación de docentes esté en ejecución a la vez.</p>
   *
   * <p>La expresión cron utilizada es  cada minuto.</p>
   *
   * <p>Durante la ejecución, el método realiza las siguientes acciones:
   * <ul>
   *   <li>Registra el inicio del proceso con una marca de tiempo.</li>
   *   <li>Invoca el servicio de matriculación de Moodle para matricular a los docentes.</li>
   *   <li>Registra el final del proceso con una marca de tiempo.</li>
   * </ul>
   * </p>
   *
   * <p>Si ocurre una excepción durante el proceso, se registra un mensaje de error en los logs.</p>
   *
   * <p>Si el bloqueo no se puede adquirir (es decir, si otro proceso ya está en ejecución),
   * se registra un mensaje informando que el proceso se omitió.</p>
   *
   * @see MatriculaMoodleService#matricularDocentesMoodle()
   */
  //@Scheduled(cron = "*/1 * * * * ?")
  public void ejecutarMatriculaDocente() {
    if (lock.tryLock()) {
      try {
        log.info("Inicia matricularDocentesMoodle: " + System.currentTimeMillis());
        matriculaMoodleService.matricularDocentesMoodle();
        log.info("Finaliza ejecutarMatriculaEstudiante: " + System.currentTimeMillis());
      } catch (Exception e) {
        log.error("Error en matricularDocentesMoodle", e);
      } finally {
        lock.unlock();
      }
    } else {
      log.info("Cron ya está en ejecución matricularDocentesMoodle, omitiendo ejecución...");
    }
  }

  /**
   * Método programado para desmatricular docentes en Moodle.
   * Este método se ejecuta cada minuto según la expresión cron especificada.
   * <p>
   * El método intenta adquirir un bloqueo antes de proceder con la ejecución
   * para evitar múltiples ejecuciones concurrentes. Si el bloqueo es adquirido,
   * se registra el inicio de la operación, se llama al servicio de matrícula
   * para desmatricular a los docentes, y finalmente se registra la finalización
   * de la operación. Si ocurre una excepción durante la ejecución, se registra
   * un error en los logs.
   *
   * Si no se puede adquirir el bloqueo, se registra un mensaje informando que
   * el cron ya está en ejecución y se omite la ejecución.
   * Indica que el método se ejecuta cada minuto.
   * </p>
   */
  //@Scheduled(cron = "*/1 * * * * ?")
  public void ejecutarDesmatriculaDocentesMoodle() {
    if (lock.tryLock()) {
      try {
        log.info("Inicia desmatriculaDocentesMoodle: " + System.currentTimeMillis());
        matriculaMoodleService.desmatriculaDocentesMoodle();
        log.info("Finaliza desmatriculaDocentesMoodle: " + System.currentTimeMillis());
      } catch (Exception e) {
        log.error("Error en desmatriculaDocentesMoodle", e);
      } finally {
        lock.unlock();
      }
    } else {
      log.info("Cron ya está en ejecución desmatriculaDocentesMoodle, omitiendo ejecución...");
    }
  }

}
