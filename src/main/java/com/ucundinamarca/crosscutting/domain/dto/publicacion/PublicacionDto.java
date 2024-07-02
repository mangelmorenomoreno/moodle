package com.ucundinamarca.crosscutting.domain.dto.publicacion;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * PublicacionDto.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */

@Setter
@Getter
@Builder
public class PublicacionDto {

  private Long postId;
  private String titulo;
  private String contenido;
}
