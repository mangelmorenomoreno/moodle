package com.ucundinamarca.crosscutting.domain.dto.autentication;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ConexionVo.
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
public class ConexionVo {

  private String url;
  private String wstoken;
  private String moodlewsrestformat;
  private String wsfunction;

}
