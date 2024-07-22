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
 * UsuarioMoodle.
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
@Table(name = "USUARIOMOODLE", schema = "CAMPOSAPRENDIZAJEUDEC")
public class UsuarioMoodle {


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuariomoodle_seq")
  @SequenceGenerator(name = "usuariomoodle_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_USMO_ID",
      allocationSize = 1)
  @Column(name = "USMO_ID")
  private Integer usmoId;

  @Column(name = "INST_ID", nullable = false)
  private Integer instId;

  @Column(name = "TIUS_ID", nullable = false)
  private Integer tiusId;

  @Column(name = "PEGE_ID", nullable = false)
  private Integer pegeId;

  @Column(name = "USMO_USUARIO", nullable = false)
  private String usmoUsuario;

  @Column(name = "AREA_ID")
  private Integer areaId;

  @Column(name = "PROG_ID")
  private Integer progId;

  @Column(name = "ESTP_ID")
  private Integer estpId;

  @Column(name = "UNID_ID")
  private Integer unidId;

  @Column(name = "USMO_REGISTRADOPOR")
  private String usmoRegistradoPor;

  @Column(name = "USMO_FECHACAMBIO")
  private Timestamp usmoFechaCambio;

  @Column(name = "USMO_IDMOODLE")
  private String usmoIdMoodle;


}
