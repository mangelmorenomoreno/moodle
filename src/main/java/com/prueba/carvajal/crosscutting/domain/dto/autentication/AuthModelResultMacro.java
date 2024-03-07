package com.prueba.carvajal.crosscutting.domain.dto.autentication;


import com.prueba.carvajal.crosscutting.domain.dto.user.UserModelMacro;
import lombok.Builder;
import lombok.Data;

/**
 * AuthModelResultMacro
 * @author miguel.moreno
 * @since 07-03-2024
 */

@Data
@Builder
public class AuthModelResultMacro {

    private String accessToken;
    private Integer userId;
    private UserModelMacro userInfo;
    private boolean statusPassword;
}
