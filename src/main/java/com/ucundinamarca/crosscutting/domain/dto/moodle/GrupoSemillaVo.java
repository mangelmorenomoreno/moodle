package com.ucundinamarca.crosscutting.domain.dto.moodle;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * GrupoSemillawsVo.
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
public class GrupoSemillaVo {

  private String grseId;
  private String semoId;
  private String grseNombrelargo;
  private String grseRegistradopor;
  private String peunId;
  private String grupId;
  private Timestamp grseFechacambio;
  private String instId;
  private String grseNombrecorto;
  private String grseIdmoodle;

}
