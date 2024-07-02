package com.ucundinamarca.crosscutting.domain.dto.comentario;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * ComentarioDtoFind.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
@Getter
@Setter
@Builder
public class ComentarioDtoFind {
  private Integer postId;
}

