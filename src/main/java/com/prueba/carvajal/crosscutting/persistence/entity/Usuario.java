package com.prueba.carvajal.crosscutting.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa a un usuario en la aplicación.
 * Está mapeada a la tabla 'usuarios' en la base de datos.
 * Incluye detalles como el nombre, correo electrónico, contraseña y la fecha de creación.
 * Usuario
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "usuarios", schema = "facetime")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "nombre", nullable = false, length = 100)
  private String nombre;

  @Column(name = "apellido", length = 100)
  private String apellido;

  @Column(name = "correo_electronico", nullable = false, unique = true, length = 150)
  private String correoElectronico;


  @Column(name = "fecha_creacion", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Column(name = "estado")
  private Boolean estado;
}
