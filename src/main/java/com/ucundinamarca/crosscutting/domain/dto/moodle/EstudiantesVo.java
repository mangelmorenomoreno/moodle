package com.ucundinamarca.crosscutting.domain.dto.moodle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * EstudiantesVo.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstudiantesVo {

  private String unidNombre;
  private Integer pegeId;
  private String instancia;
  private String cateDescripcion;
  private String pegeDocumentoidentidad;
  private String progNombre;
  private String pengEmailinstitucional;
  private String pengPrimernombre;
  private String siteDescripcion;
  private String facultad;
  private String codigo;
  private String estado;
  private String pengPrimerapellido;
  private String tiumNombre;
  private String usuaUsuario;
  private String pengSegundoapellido;
  private String pengSegundonombre;
  private Integer estpId;
  private Integer progId;
  private Integer unidId;
  private Integer niedId;
  private String niedDescripcion;

}
