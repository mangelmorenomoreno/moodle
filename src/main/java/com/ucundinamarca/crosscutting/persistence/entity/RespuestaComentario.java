package com.ucundinamarca.crosscutting.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * RespuestaComentario.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "respuestas_comentarios")
public class RespuestaComentario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "respuesta_id")
  private Long respuestaId;

  @ManyToOne
  @JoinColumn(name = "comment_id",  nullable = false)
  private Comentario comentario;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Usuario usuario;

  @Column(nullable = false)
  private String contenido;

  @Column(name = "fecha_respuesta")
  private LocalDateTime fechaRespuesta;

}
