package com.ucundinamarca.crosscutting.persistence.repository;


import com.ucundinamarca.crosscutting.persistence.entity.Credencial;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Credencial.
 * Facilita operaciones CRUD y permite definir consultas específicas
 * relacionadas con las Credenciales.
 *
 * @author miguel.moreno
 * @version 1.0
 */
@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Integer> {

  Optional<Credencial> findByUsuarioUserId(Integer userId);

}
