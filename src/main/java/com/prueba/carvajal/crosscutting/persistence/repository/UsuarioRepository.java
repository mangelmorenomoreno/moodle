package com.prueba.carvajal.crosscutting.persistence.repository;

import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Usuario.
 * Proporciona operaciones CRUD automáticas y capacidad para definir consultas personalizadas para Usuarios.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
  public Usuario findByUserId(Integer userId);

  public Usuario findByCorreoElectronico(String correoElectronico);

  @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:valor% OR u.apellido LIKE %:valor%  OR u.correoElectronico LIKE %:valor%")
  public List<Usuario> findByNombreAndCorreoElectronicoSimilar(@Param("valor") String valor);

}
