package com.prueba.carvajal.crosscutting.domain.errors;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class ErrorDataResponse.
 *
 * @author miguel.moreno
 * @since 2024-03-06
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
