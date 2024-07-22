package com.ucundinamarca.crosscutting.domain.dto.moodle;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UsuariosMoodleVo.
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
public class UsuariosMoodleVo {

  private String usmoId;
  private String usmoIdmoodle;
  private String progId;
  private String areaId;
  private String usmoUsuario;
  private String instId;
  private String pegeId;
  private String tiusId;
  private String estpId;
  private Timestamp usmoFechacambio;
  private String usmoRegistradopor;
  private String unidId;
}
