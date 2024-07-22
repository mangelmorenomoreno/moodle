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
 * TipoUsuario.
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
@Table(name = "TIPOUSUARIO", schema = "CAMPOSAPRENDIZAJEUDEC")
public class TipoUsuario {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipousuario_seq")
  @SequenceGenerator(name = "tipousuario_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_TIUM_ID",
      allocationSize = 1)
  @Column(name = "TIUM_ID")
  private Long tiumId;

  @Column(name = "TIUM_NOMBRE", nullable = false)
  private String tiumNombre;

  @Column(name = "TIUM_DESCRIPCION", nullable = false)
  private String tiumDescripcion;

  @Column(name = "TIUM_REGISTRADOPOR")
  private String tiumRegistradoPor;

  @Column(name = "TIUM_FECHACAMBIO")
  private Timestamp tiumFechaCambio;
}
