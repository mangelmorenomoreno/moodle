package com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * RolMoodle.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "MATRICULAMOODLE", schema = "CAMPOSAPRENDIZAJEUDEC")
public class RolMoodle {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rolmoodle_seq")
  @SequenceGenerator(name = "rolmoodle_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_ROMO_ID",
      allocationSize = 1)
  @Column(name = "ROMO_ID")
  private Long romoId;

  @Column(name = "ROMO_NOMBRE", nullable = false)
  private String romoNombre;

  @Column(name = "ROMO_DESCRIPCION")
  private String romoDescripcion;

  @Column(name = "ROMO_ESTADO", nullable = false)
  private String romoEstado;

  @Column(name = "ROMO_REGISTRADOPOR")
  private String romoRegistradoPor;

  @Column(name = "ROMO_FECHACAMBIO")
  private Timestamp romoFechaCambio;

}
