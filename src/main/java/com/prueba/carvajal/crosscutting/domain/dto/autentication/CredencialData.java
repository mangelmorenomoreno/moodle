package com.prueba.carvajal.crosscutting.domain.dto.autentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CredencialData {

  private String passwordOld;
  private String passwordNew;

}
