package com.prueba.carvajal.crosscutting.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserModelMacro.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModelMacro {
  private String userName;
  private String email;
  private Integer userId;
}
