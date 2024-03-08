package com.prueba.carvajal.crosscutting.persistence.repository;


import com.prueba.carvajal.crosscutting.persistence.entity.Credencial;
import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Credencial.
 * Facilita operaciones CRUD y permite definir consultas específicas relacionadas con las Credenciales.
 */
@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Integer> {

  Optional<Credencial> findByUsuarioUserId(Integer userId);

}
