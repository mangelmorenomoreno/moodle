package com.ucundinamarca.crosscutting.domain.dto.moodle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MatriculaMoodlewsVO.
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
public class MatriculaMoodlewsVo {

  private String courseid;
  private String userid;
  private String roleid;
  private String usmoId;
  private String grseId;

}
