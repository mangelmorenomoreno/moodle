package com.prueba.carvajal.crosscutting.domain.dto.autentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LoginData
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginData {
  private String email;
  private String password;
}
