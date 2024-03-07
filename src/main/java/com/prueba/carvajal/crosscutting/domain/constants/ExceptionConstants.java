package com.prueba.carvajal.crosscutting.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ExceptionConstants
 *
 * @author Charles Edinson Gomez Sanchez
 * @version 1.0
 * @since 2022-06-13
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstants {

    public static final String CREDENTIALS_NOT_CORRECT = "el usuario o la contraseña son incorrectos";
    public static final String LOGIN_ERROR = "error al tratar de iniciar sesion";
    public static final String LIST_USER_PROFILE_ERROR = "Error al listar los perfiles de los usuarios";
    public static final String GET_USER_PROFILE_ERROR = "Error al obtener el perfil del usuario";
    public static final String FIND_ALL_ROLES_ERROR = "Error al listar los roles";
    public static final String FIND_ALL_INTERNAL_ROLES_ERROR = "Error al listar los roles de tipo interno";
    public static final String FIND_BYID_ERROR = "Error al encontrar por id ";
    public static final String FIND_ALLPAGE_ERROR = "Error al encontrar la pagina ";
    public static final String UPDATE_ROLE_ERROR =
            "Se ha presentado un fallo actualizando el rol";
    public static final String DELETE_ROLE_ERROR = "El rol no existe";
    public static final String LIST_OPTION_MENU_ERROR = "Error al listar opciones de menu";
    public static final String LIST_OPTION_MICROSITE_MENU_ERROR = "Error al listar las opciones de menu del micrositio";
    public static final String LIST_CREDENTIAL_ERROR = "Error al listar las credenciales";
    public static final String LIST_USER_TYPE_ERROR = "Error al listar tipos de rol";
    public static final String FIND_USER_TYPE_ERROR = "Error al buscar el tipo de rol";
    public static final String CREATE_ROLE_ERROR = "Se ha presentado un fallo creando el rol";
    public static final String FIND_ROLE_NAME_ERROR = "El rol ya existe";
    public static final String FIND_ALL_PRODUCT_OPTIONS_ERROR = "Error al listar las opciones por producto";
    public static final String FIND_ALL_TRANSVERSAL_OPTIONS_ERROR = "Error al listar las opciones transversales";
    public static final String FIND_ALL_ROLE_PERMISSIONS_ERROR = "Error al listar las opciones permitidas por el rol";
    public static final String FIND_ALL_USER_PERMISSIONS_ERROR = "Error al listar las opciones permitidas del usuario";
    public static final String FIND_CITY_ID_ERROR = "Se ha presentado un error al seleccionar la ciudad";
    public static final String FIND_COMPANY_ID_ERROR =
        "Se ha presentado un error al buscar la informacion de la empresa";
    public static final String FIND_ROLES_BY_BUSINESS_GROUP_ERROR =
        "Error al buscar los roles de acuerdo al grupo empresarial";
    public static final String SAVE_USER_ROLES_ERROR_AVALIABLE = " Una de las companias no " +
            "tiene licencias disponibles ";
    public static final String SAVE_CONFIG_NOTIFICATION_ERROR =
        "Error al guardar la configuracion de las notificaciones por defecto para la compania";
    
    public static final String SEND_NOTIFICATION_ERROR = "Error al enviar el correo electronico";

    public static final String UPDATE_USER_ROLES_ERROR_DELETE = "Error al desasociar los roles al usuario";
    public static final String RESTORE_SEND_EMAIL_PASSWORD_ERROR =
        "Error al enviar el correo de reestablecimiento de contrasena";
    public static final String CUSTOM_ERROR =
        "Error personalizado";
    public static final String FIND_USER_COMPANY_INFO_PROFILE =
        "Error al buscar la informacion de las empresas del usuario";
    public static final String LIST_TIME_ZONES_ERROR = "Error al listar las zonas horarias";
    public static final String LIST_COMPANY_BY_USER_ERROR =
        "Error al listar las empresas a las que pertenece el usuario";
    public static final String EMPTY_PARAMETERS = "Error al procesar, no se recibio ningun parametro";
    public static final String WITHOUT_RESULTS = "No se encontraron registros";
    public static final String PAGINAR_ERROR = "Error al paginar";
    public static final String CREATE_CREDENTIAL_ERROR = "Error al crear la credencial";
    public static final String UPDATE_CREDENTIAL_ERROR = "Error al actualizar la credencial";
    public static final String SAVE_COMPANY_USER_ERROR = "Error al guardar las empresas del usuario";
    public static final String SAVE_ROLES_USER_ERROR = "Error al asociar los roles al usuario";
    public static final String FIND_USER_CREDETENDIAL_ERROR = "Error al obtener la credencial del usuario";
    public static final String DELETE_COMPANY_USER_ERROR = "Error al eliminar la asociación del usuario y la empresa";
    public static final String DECODE_TOKEN_ERROR = "Error al intentar decodificar el token";
    public static final String ERROR_BUILD_ROLE = "Error al intentar construir el objeto Role";
    public static final String ERROR_FIND_ALL_ROLE = "Error while fetching roles";
    public static final String UPDATE_LANGUAGE_ERROR = "Error al actualizar el lenguaje del usuario";
    public static final String TIME_ZONE_USER_ERROR = "Error al listar la zona horaria del usuario";
    public static final String UPDATE_USER_TIME_ZONE_ERROR = "Error al actualizar la zona horaria del usuario";
}
