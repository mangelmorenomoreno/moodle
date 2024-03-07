package com.prueba.carvajal.crosscutting.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ResponseValueConstants
 *
 * @author Juan Manuel Herrera
 * @version 1.0
 * @since 7-04-2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseValueConstants {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String LOGIN = "LOGIN";
    public static final String TOKEN_EXPIRED = "El token ha expirado";
    public static final String TOKEN_INVALID = "El token es invalido";
    public static final String OK = "OK";
    public static final String LOG_OUT = "LOG-OUT";
    public static final String PROCESS = "PROCESS";
    public static final String CONTENT_TYPE = "application/json";
    public static final String CONTENT = "Content-Type";
    public static final String ERROR_INSERT_LOGIN_LOG = "Error inserting LoginLog record ";
    public static final String ERROR_VALUE_TOKEN = "Cannot read token value";
    public static final String UNABLE_TO_READ = "Unable to read";
    public static final String JWT_NOT_MATCH_LOCALLY = "JWT signature does not match locally computed signature";
    public static final String JWT_EXACTLY = "JWT strings must contain exactly 2 period characters.";

}
