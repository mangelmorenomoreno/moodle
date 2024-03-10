package com.prueba.carvajal.crosscutting.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * SecurityConstants.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

  public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
  public static final String JWT_HEADER = "Authorization";
  public static final String EXPIRED = "expired";
  public static final String USERNAME = "username";

  public static final String EMAIL = "mail";
  public static final String IDUSER = "idUser";

  public static final String IDUSERTYPE = "idUserType";

  public static final String COMPANY = "company";

  public static final String TIME_ZONE = "timeZone";

  public static final String LANGUAGE = "language";

  public static final String BUSINESSGROUP = "businessGroup";
  public static final String AUTHORITIES = "authorities";
  public static final String ID_COMPANY = "idCompany";
  public static final String JWT_TOKEN = "JWT Token";
  public static final String CARVAJAL = "Carvajal";
  public static final Integer EXPIRATION = 30000000;
  public static final String PERMISSIONS = "permissions";
  public static final String ID_LOG = "idLog";
  public static final String ID_IDENTIFICATION_TYPE = "idIdentificationType";
}
