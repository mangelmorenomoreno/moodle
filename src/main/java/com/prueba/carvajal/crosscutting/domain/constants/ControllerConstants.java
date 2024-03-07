package com.prueba.carvajal.crosscutting.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ControllerConstants
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 06-03-2024
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerConstants {

    public static final String USUARIO_URL = "usuario";
    public static final String COMENTARIO_URL = "comentario";
    public static final String INFORMACION_USUARIO_URL = "findByToken";
    public static final String INFORMACION_USUARIO_CORREO_URL = "findByEmail/{email}";
    public static final String LOGIN = "login";
    public static final String NOTIFICACION_URL = "notificacion";
    public static final String PUBLICACION_URL = "publicacion";



}
