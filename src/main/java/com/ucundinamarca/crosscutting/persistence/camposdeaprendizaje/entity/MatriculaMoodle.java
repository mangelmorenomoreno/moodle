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
 * MatriculaMoodle.
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
public class MatriculaMoodle {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matriculamoodle_seq")
  @SequenceGenerator(name = "matriculamoodle_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_MAMO_ID",
      allocationSize = 1)
  @Column(name = "MAMO_ID")
  private Long mamoId;

  @Column(name = "USMO_ID", nullable = false)
  private Long usmoId;

  @Column(name = "GRSE_ID", nullable = false)
  private Long grseId;

  @Column(name = "ROMO_ID", nullable = false)
  private Long romoId;

  @Column(name = "MAMO_REGISTRADOPOR")
  private String mamoRegistradoPor;

  @Column(name = "MAMO_FECHACAMBIO")
  private Timestamp mamoFechaCambio;
}
