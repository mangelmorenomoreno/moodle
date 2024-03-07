package com.prueba.carvajal.crosscutting.domain.errors;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ErrorDataResponse
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDataResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 8888044182040496270L;

  private String code;
  private Object message;
  private Object detail;
}
