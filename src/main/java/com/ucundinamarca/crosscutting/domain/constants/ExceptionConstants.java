package com.ucundinamarca.crosscutting.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ExceptionConstants.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2022-06-13
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstants {
  public static final String DECODE_TOKEN_ERROR = "Error al intentar decodificar el token";
}
