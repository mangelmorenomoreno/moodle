package com.ucundinamarca.crosscutting.domain.dto.autentication;


import com.ucundinamarca.crosscutting.domain.dto.user.UserModelMacro;
import lombok.Builder;
import lombok.Data;

/**
 * AuthModelResultMacro.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */

@Data
@Builder
public class AuthModelResultMacro {
  private String accessToken;
  private Integer userId;
  private UserModelMacro userInfo;
  private boolean statusPassword;
}
