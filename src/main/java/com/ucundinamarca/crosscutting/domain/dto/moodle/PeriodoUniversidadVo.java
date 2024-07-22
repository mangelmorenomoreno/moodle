package com.ucundinamarca.crosscutting.domain.dto.moodle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * PeriodoUniversidadVo.
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
public class PeriodoUniversidadVo {

  private String peunId;
  private String peunPeriodo;
  private String peunAno;

}
