package com.ucundinamarca.crosscutting.domain.dto.autentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LoginData.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
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
