package com.ucundinamarca.crosscutting.domain.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MessageCodes.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
@AllArgsConstructor
@ToString
@NoArgsConstructor
public enum MessageCodes {

  GET_USER_PROFILE_001("Información del perfil del usuario"),
  PROCESS_SUCCES("Procesado Correctamente"),
  TRUE("TRUE");


  private String message;

  public String getMessage() {
    return message;
  }

}
