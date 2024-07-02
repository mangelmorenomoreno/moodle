package com.ucundinamarca.crosscutting.domain.dto.comentario;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * ComentarioDto.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
@Getter
@Setter
@Builder
public class ComentarioDto {
  private Integer postId;
  private String contenido;
}
