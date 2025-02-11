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
 * NotasMoodle.
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
@Table(name = "NOTASMOODLE", schema = "CAMPOSAPRENDIZAJEUDEC")
public class NotasMoodle {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notasmoodle_seq")
  @SequenceGenerator(name = "notasmoodle_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_NOMO_ID",
      allocationSize = 1)
  @Column(name = "NOMO_ID")
  private Long nomoId;

  @Column(name = "PEUN_ID", nullable = false)
  private Long peunId;

  @Column(name = "GRSE_IDMOODLE", nullable = false)
  private Long grseIdMoodle;

  @Column(name = "USMO_IDMOODLE", nullable = false)
  private Long usmoIdMoodle;

  @Column(name = "NOMO_ITEMNAME", nullable = false)
  private String nomoItemName;

  @Column(name = "NOMO_COURSE", nullable = false)
  private String nomoCourse;

  @Column(name = "NOMO_FINALGRADECOURSE", nullable = false)
  private String nomoFinalGradeCourse;

  @Column(name = "NOMO_CATEGORY")
  private String nomoCategory;

  @Column(name = "NOMO_REGISTRADOPOR")
  private String nomoRegistradoPor;

  @Column(name = "NOMO_FECHACAMBIO")
  private Timestamp nomoFechaCambio;

  @Column(name = "NOMO_USERNAME")
  private String nomoUsername;


}
