package com.ucundinamarca.crosscutting.domain.dto.estudiantes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UsuariowsVo.
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
public class UsuariowsVo {

  private String email;
  private String lastname;
  private String tipo;
  private String firstname;
  private String pasword;
  private String username;
  private String programa;
  private String facultad;
  private String sede;
  private String identificacion;
  private String codigoEstudiante;
  private String id;

}
