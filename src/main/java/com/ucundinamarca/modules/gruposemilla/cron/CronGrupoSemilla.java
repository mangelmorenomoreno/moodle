package com.ucundinamarca.modules.gruposemilla.cron;

import com.ucundinamarca.modules.gruposemilla.usecase.GrupoSemillaService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * CronGrupoSemilla.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Service
@Log4j2
public class CronGrupoSemilla {

  @Autowired
  private GrupoSemillaService grupoSemillaService;

  private final Lock lock = new ReentrantLock();


  /**
   * Ejecuta la creación masiva de grupos en Moodle cada segundo.
   * <p>
   * Este método está programado para ejecutarse cada segundo utilizando una expresión cron.
   * Utiliza un bloqueo (lock) para asegurarse de que no se ejecuten múltiples instancias de
   * este método al mismo tiempo.
   * </p>
   * <p>
   * Si el bloqueo se adquiere con éxito, el método llama a
   * {@link GrupoSemillaService#registarGrupoMasivo()}
   * para realizar la creación masiva de grupos. Los registros de inicio y fin del proceso se
   * registran con la marca de tiempo actual. En caso de que ocurra una excepción durante
   * la ejecución,
   * se registra un mensaje de error.
   * </p>
   * <p>
   * Si no se puede adquirir el bloqueo, se registra un mensaje indicando
   * que el cron ya está en ejecución
   * y se omite la ejecución actual.
   * </p>
   */
  @Scheduled(cron = "*/1 * * * * ?")
  public void ejecutarGruposSemilla() {
    if (lock.tryLock()) {
      try {
        log.info("Inicia registarGrupoMasivo: " + System.currentTimeMillis());
        grupoSemillaService.registarGrupoMasivo();
        log.info("Finaliza registarGrupoMasivo: " + System.currentTimeMillis());
      } catch (Exception e) {
        log.error("Error en registarGrupoMasivo", e);
      } finally {
        lock.unlock();
      }
    } else {
      log.info("Cron ya está en ejecución registarGrupoMasivo, omitiendo ejecución...");
    }
  }


}
