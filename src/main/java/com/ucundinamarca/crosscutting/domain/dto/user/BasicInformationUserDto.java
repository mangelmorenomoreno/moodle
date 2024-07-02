package com.ucundinamarca.crosscutting.domain.dto.user;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BasicInformationUserDto.
 *
 * @author Miguel Angel Moreno
 * @version 1.0
 * @since 07-02-2023
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicInformationUserDto implements Serializable {

  private static final long serialVersionUID = 1L;
  private String nombre;
  private String apellido;
  private String correo;

}
