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
 * GrupoSemilla.
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
@Table(name = "GRUPOSEMILLA", schema = "CAMPOSAPRENDIZAJEUDEC")
public class GrupoSemilla {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gruposemilla_seq")
  @SequenceGenerator(name = "gruposemilla_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_GRSE_ID",
      allocationSize = 1)
  @Column(name = "GRSE_ID")
  private Long grseId;

  @Column(name = "SEMO_ID", nullable = false)
  private Long semoId;

  @Column(name = "GRUP_ID")
  private Long grupId;

  @Column(name = "PEUN_ID")
  private Long peunId;

  @Column(name = "INST_ID", nullable = false)
  private Long instId;

  @Column(name = "GRSE_IDMOODLE", nullable = false)
  private String grseIdMoodle;

  @Column(name = "GRSE_NOMBRECORTO", nullable = false)
  private String grseNombreCorto;

  @Column(name = "GRSE_NOMBRELARGO", nullable = false)
  private String grseNombreLargo;

  @Column(name = "GRSE_REGISTRADOPOR")
  private String grseRegistradoPor;

  @Column(name = "GRSE_FECHACAMBIO")
  private Timestamp grseFechaCambio;


}
