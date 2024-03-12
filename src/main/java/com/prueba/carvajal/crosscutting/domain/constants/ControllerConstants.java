package com.prueba.carvajal.crosscutting.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ControllerConstants.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 06-03-2024
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerConstants {
  public static final String RESPUESTA_COMENTARIO_URL = "respuestaComentario";
  public static final String USUARIO_URL = "usuario";
  public static final String RESTABLECER_PASSWORD_URL = "resetPassword/{email}";

  public static final String ACTUALIZAR_CREDENCIAL_URL = "update";
  public static final String REGISTRAR_CREDENCIAL_URL = "create";
  public static final String CREDENCIAL_URL = "credencial";
  public static final String CREAR_USUARIO_URL = "save";
  public static final String CREAR_PUBLICACION_URL = "save";
  public static final String CREAR_COMENTARIO_URL = "save";
  public static final String ELIMINAR_COMENTARIO_URL = "delete";
  public static final String ELIMINAR_PUBLICACION_URL = "delete";
  public static final String ACTUALIZAR_PUBLICACION_URL = "update";
  public static final String LISTAR_PUBLICACION_URL = "list";
  public static final String LISTAR_COMENTARIO_URL = "list";
  public static final String LISTAR_RESPUESTA_URL = "list";
  public static final String GUARDAR_RESPUESTA_URL = "save";
  public static final String ACTUALIZAR_USUARIO_URL = "update";
  public static final String COMENTARIO_URL = "comentario";
  public static final String INFORMACION_USUARIO_URL = "findByToken";
  public static final String INFORMACION_USUARIO_O_CORREO_URL = "findByData/{valor}";
  public static final String INFORMACION_USUARIO_CORREO_URL = "findByEmail/{email}";
  public static final String LOGIN = "login";
  public static final String NOTIFICACION_URL = "notificacion";
  public static final String PUBLICACION_URL = "publicacion";



}
