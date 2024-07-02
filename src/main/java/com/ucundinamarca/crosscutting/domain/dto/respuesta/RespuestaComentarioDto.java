package com.ucundinamarca.crosscutting.domain.dto.respuesta;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * RespuestaComentarioDto.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
@Setter
@Getter
@Builder
public class RespuestaComentarioDto {
  private String contenido;
  private Integer commentId;
}
