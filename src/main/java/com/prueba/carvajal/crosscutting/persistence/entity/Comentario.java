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
 * Entidad que representa un comentario en una publicación.
 * Está mapeada a la tabla 'comentarios' en la base de datos.
 * Incluye el contenido del comentario, la publicación a la que pertenece y el usuario que lo hizo.
 * Comentario
 *
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
@Table(name = "comentarios")
public class Comentario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = true)
  private Publicacion publicacion;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Usuario usuario;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String contenido;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaComentario;

  @ManyToOne
  @JoinColumn(name = "comment_parent_id", nullable = true)
  private Comentario comentarioPadre;

}
