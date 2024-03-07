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
  public static final String CONSULTAR_USUARIO_CORREO_NOTES = "Consulta la informacion del usuario por correo";
  public static final String CONSULTAR_USUARIO_VALUE = "Informacion Usuario";
  public static final String CONSULTAR_USUARIO_CORREO_VALUE = "Informacion Usuario por correo";
  public static final String CONSULTAR_USUARIO_NICK = "Usuario";
  public static final String CONSULTAR_USUARIO_CORREO_NICK = "Usuario por correo";
  public static final String LOGIN = "login";
  public static final String LOGIN_NOTES = "Para ingresar se requiere usuario y Contraseña";
  public static final String LOGIN_VALUE = "Ingreso de Usuario";
  public static final String LOGIN_NICK = "Login";
  public static final String UPDATE_TRANSACTION_OBJECT = "Update Transaction object";
  public static final String UPDATE_EXISTING_TRANSACTION = "Update existing Transaction";
  public static final String SUCCESS = "success";
  public static final String ERROR = "error";

}