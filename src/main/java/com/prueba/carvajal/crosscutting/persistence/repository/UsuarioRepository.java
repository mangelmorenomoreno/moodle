package com.prueba.carvajal.crosscutting.persistence.repository;

import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Usuario.
 * Proporciona operaciones CRUD automáticas y capacidad para definir consultas personalizadas para Usuarios.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
  public Usuario findByUserId(Integer userId);

  public Usuario findByCorreoElectronico(String correoElectronico);
}
