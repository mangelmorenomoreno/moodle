package com.prueba.carvajal.crosscutting.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
 * Entidad que representa una notificación enviada a un usuario.
 * Está mapeada a la tabla 'notificaciones' en la base de datos.
 * Incluye el tipo de notificación, su estado (leído o no leído), y la fecha de envío.
 *
 * Notificacion
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 *
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "notificaciones")
public class Notificacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long notificationId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Usuario usuario;

  @Column(nullable = false, length = 50)
  private String tipo;

  @Column(nullable = false, length = 20)
  private String estado;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaNotificacion;

}
