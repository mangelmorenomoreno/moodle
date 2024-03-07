package com.prueba.carvajal.crosscutting.domain.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * ValidationError
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Data
@Builder
@AllArgsConstructor
public class ValidationError {

  private String translationCode;
  private String detail;
  private String lang;

}
