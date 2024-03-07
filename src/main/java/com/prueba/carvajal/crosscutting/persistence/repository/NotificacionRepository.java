package com.prueba.carvajal.crosscutting.persistence.repository;

import com.prueba.carvajal.crosscutting.persistence.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repositorio JPA para la entidad Notificacion.
 * Provee operaciones CRUD automáticas y la capacidad de añadir consultas específicas para Notificaciones.
 */
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
}
