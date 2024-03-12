package com.prueba.carvajal.crosscutting.domain.constants;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ApiDocumentationConstant.
 *
 * @author miguel.moreno
 * @since 2024-03-06
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiDocumentationConstant {


  public static final String CONSULTAR_USUARIO_NOTES = "Consulta la informacion del usuario";
  public static final String CREAR_USUARIO_NOTES = "Crear usuario";
  public static final String CREAR_PUBLICACION_NOTES = "Crear publicacion";
  public static final String CREAR_PUBLICACION_VALUE = "publicacionDto, token";
  public static final String CREAR_PUBLICACION_NICK = "new publicacion";
  public static final String CREAR_COMENTARIO_NOTES = "Crear publicacion";
  public static final String CREAR_COMENTARIO_VALUE = "publicacionDto, token";
  public static final String CREAR_COMENTARIO_NICK = "new publicacion";

  public static final String ELIMINAR_COMENTARIO_NOTES = "Eliminar publicacion";
  public static final String ELIMINAR_COMENTARIO_VALUE = "publicacionDto, token";
  public static final String ELIMINAR_COMENTARIO_NICK = "Eliminar publicacion";
  public static final String ELIMINAR_PUBLICACION_NOTES = "Crear publicacion";
  public static final String ELIMINAR_PUBLICACION_VALUE = "publicacionDto, token";
  public static final String ELIMINAR_PUBLICACION_NICK = "new publicacion";
  public static final String ACTUALIZAR_PUBLICACION_NOTES = "Modificar publicacion";
  public static final String ACTUALIZAR_PUBLICACION_VALUE = "publicacionDto, token";
  public static final String ACTUALIZAR_PUBLICACION_NICK = "actualizar publicacion";
  public static final String LISTAR_PUBLICACION_NOTES = "Listar publicacion";
  public static final String LISTAR_PUBLICACION_VALUE = "token";
  public static final String LISTAR_PUBLICACION_NICK = "list publicacion";


  public static final String LISTAR_COMENTARIO_NOTES = "Listar comentarios";
  public static final String LISTAR_COMENTARIO_VALUE = "token";
  public static final String LISTAR_COMENTARIO_NICK = "list comentarios";

  public static final String GUARDAR_COMENTARIO_NOTES = "Guardar comentarios";
  public static final String GUARDAR_COMENTARIO_VALUE = "token";
  public static final String GUARDAR_COMENTARIO_NICK = "Guardar comentarios";
  public static final String ACTUALIZAR_USUARIO_NOTES = "Modificar usuario";
  public static final String CONSULTAR_USUARIO_CORREO_NOTES = "Consulta la informacion del "
                                                                + "usuario por correo";
  public static final String CONSULTAR_USUARIO_VALUE = "Informacion Usuario";
  public static final String CREAR_USUARIO_VALUE = "Crea Informacion Usuario";
  public static final String ACTUALIZAR_USUARIO_VALUE = "Modifica Informacion Usuario";
  public static final String CONSULTAR_USUARIO_CORREO_VALUE = "Informacion Usuario por correo";
  public static final String CONSULTAR_USUARIO_NICK = "Usuario";
  public static final String CREAR_USUARIO_NICK = "save";
  public static final String ACTUALIZAR_USUARIO_NICK = "Update";
  public static final String CONSULTAR_USUARIO_CORREO_NICK = "Usuario por correo";
  public static final String LOGIN = "login";
  public static final String CREDENCIAL_UPDATE_NICK = "Actualizar Password";
  public static final String CREDENCIAL_UPDATE_NOTES = "Se requiere token de actualizacion";
  public static final String CREDENCIAL_UPDATE_VALUE = "Actualizar credenciales";

  public static final String CREDENCIAL_CREATE_NICK = "Registrar "
                                                        + "Contraseña nueva o de recuperacion ";
  public static final String CREDENCIAL_CREATE_NOTES = "Registrar "
                                                         + "Contraseña nueva o de recuperacion ";
  public static final String CREDENCIAL_CREATE_VALUE = "Registrar "
                                                         + "Contraseña nueva o de recuperacion ";
  public static final String RESET_CREDENCIAL_NOTES = "Se envia correo para actualizar Contraseña";
  public static final String RESET_CREDENCIAL_VALUE = "Restablecer Contraseña";
  public static final String RESET_CREDENCIAL_NICK = "Restablecer Contraseña";
  public static final String LOGIN_NOTES = "Para ingresar se requiere usuario y Contraseña";
  public static final String LOGIN_VALUE = "Ingreso de Usuario";
  public static final String LOGIN_NICK = "Login";
  public static final String ACTIVATE_USER = "Activacion de usuario";

}