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
 * Entidad que representa una publicación hecha por un usuario.
 * Está mapeada a la tabla 'publicaciones' en la base de datos.
 * Contiene información como el título, contenido, usuario que la hizo y la fecha de publicación.
 *
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "publicaciones")
public class Publicacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Usuario usuario;

  @Column(length = 200)
  private String titulo;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String contenido;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaPublicacion;

}
