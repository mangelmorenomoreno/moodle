package com.prueba.carvajal.crosscutting.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserModelMacro
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModelMacro {
    private String userName;
    private String email;
    private Integer userId;
}
