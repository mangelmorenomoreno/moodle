package com.prueba.carvajal.crosscutting.domain.dto.user;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BasicInformationUserDTO
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
public class BasicInformationUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private String apellido;
    private String correo;

}
