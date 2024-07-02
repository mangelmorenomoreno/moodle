package com.ucundinamarca.crosscutting.persistence.repository;

import com.ucundinamarca.crosscutting.persistence.entity.Publicacion;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Repositorio JPA para la entidad PublicacionController.
 * Permite realizar operaciones CRUD automáticas y definir consultas personalizadas para
 * Publicaciones.
 *
 * @author miguel.moreno
 * @version 1.0
 */
@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
  List<Publicacion> findByUsuario(Usuario usuario);

  @Query("SELECT p FROM Publicacion p ORDER BY p.fechaPublicacion DESC")
  List<Publicacion> findAllOrderByFechaPublicacionDesc();
}
