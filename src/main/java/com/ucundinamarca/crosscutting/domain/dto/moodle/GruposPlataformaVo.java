package com.ucundinamarca.crosscutting.domain.dto.moodle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * GruposPlataformaVO.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GruposPlataformaVo {
  private String nombrelargo;
  private String progId;
  private String progNombre;
  private String unidId;
  private String facultad;
  private String grupNombre;
  private String grupId;
  private String mateCodigomateria;
  private String peunId;
  private String unidad;
  private String pensum;
  private String pensId;
  private String semoId;
  private String niedId;
  private String niedDescripcion;
  private String grseIdmoodle;
  private String semoNombrecorto;
  private String semoNombrelargo;
  private String semoIdmoodle;
  private String cadiId;
  private String caiId;
  private String cateId;

}
