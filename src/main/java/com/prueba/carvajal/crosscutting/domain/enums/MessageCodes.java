/**
 *
 */
package com.prueba.carvajal.crosscutting.domain.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Enum que guarda los codigos y mensajes que se envian como respuesta de las apis
 * MessageCodes
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@AllArgsConstructor
@ToString
@NoArgsConstructor
public enum MessageCodes {

    LOGIN_000("El usuario o la contraseï¿½a son incorrectos"),
    LOGIN_001("El usuario se encuentra inactivo"),
    LOGIN_002("El usuario se encuentra bloqueado, por favor intï¿½ntelo mï¿½s tarde"),
    LOGIN_003("El usuario aï¿½n no ha activado su cuenta"),
    LOGIN_005("La contraseï¿½a no estï¿½ vigente"),
    LOGIN_008(
            "Tienes un proceso de restablecimiento en curso. "
                    + "Por favor continúa con este proceso mediante el correo electrónico enviado "
                    + "en el último intento para restablecer tu contraseña."),
    LOGOUT_001("SE HA CERRADO LA SESION CORRECTAMENTE"),
    LOGOUT_002("Error al registrar en las tablas auditoras"),
    LOGOUT_003("No hay un usuario asociado a ese correo"),
    ROLE_000("El rol se ha creado exitosamente"),
    ROLE_000E1("El rol no se ha creado correctamente"),
    ROLE_000E2("EL ROL NO SE HA ACTUALIZADO CORRECTAMENTE"),
    ROLE_000E3("YA EXISTEN PERMISOS REGISTRADOS EN EL ROL"),
    ROLE_000E4("EL ROL NO EXISTE"),
    ROLE_000E5("ERROR AL ELIMINAR LAS OPCIONES DE EL ROL EXISTENTE "),
    ROLE_001("El rol se ha modificado exitosamente"),
    ROLE_002("El nombre de rol ingresado ya existe"),
    ROLE_003("El rol se eliminï¿½ exitosamente"),
    ROLE_004("El rol no se puede eliminar porque tiene usuarios asociados"),
    IDENTIFICATION_TYPE_001("proceso ejecutado correctamente"),
    IDENTIFICATION_TYPE_002("Error al listar los tipos de identificacion de las empresa"),
    IDENTIFICATION_TYPE_003("El tipo de identificaciÃ³n se ha creado correctamente"),
    COMPANY_000("La empresa se ha creado satisfactoriamente"),
    COMPANY_001(
            "El tipo de identificación y la identificación principal de la empresa están asignados a otra empresa"),
    COMPANY_002(
            "El tipo de identificacion y la identificación principal de la empresa no están asignados a otra empresa"),
    EXISTS_COMPANY_IDENTIFICATION_ERROR(
            "Se ha presentado un error al validar si la identificacion de la empresa ya existe"),
    COUNTRY_001("proceso ejecutado correctamente"),
    COUNTRY_ERROR_001("Error al listar los paises de la base de datos"),
    PRODUCT_ERROR_001("Error al listar los productos"),
    ORGANIZATION_CATEGORIES_001("lista de las categorias de la organizacion"),
    ORGANIZATION_CATEGORIES_ERROR_001("Error al listar las categorias"),
    COMPANY_FILTER_001("lista de empresas segun el filtro"),
    COMPANY_FILTER_ERROR_001("Error al lista de empresas segun el filtro"),
    RAZON_SOCIAL_FILTER_ERROR_001("Error al lista la razon social de la empresa"),
    CITY_ERROR_001("error al listar las ciudades"),
    REGION_001("lista de Region segun el id del country"),
    REGION_ERROR_001("error al listar las regiones"),
    IDENTIFICATION_COMPANY_FILTER_ERROR_001("Error al lista la identificacion segun el filtro"),
    IDENTIFICATION_COMPANY_FILTER_001("Lista de identificacion segun filtro "),
    CATEGORIES_FILTER_001("Lista de categorias segun filtro"),
    CATEGORIES_FILTER_ERROR("Error al listar las categorias"),
    PRODUCT_FILTER_001("Lista de productos segun filtro"),
    PRODUCT_FILTER_ERROR_001("Error a listar los productos"),
    COMPANY_OF_BUSINESS_GROUP_001("Lista de empresas relacionados al grupo empresarial"),
    COMPANY_OF_BUSINESS_GROUP_ERROR("error al listar las empresas relacionados al grupo empresarial"),
    PRODUCT_OF_COMPANY_001(" listado de productos y usuarios por cada empresa"),
    PRODUCT_OF_COMPANY_ERROR("error al lista de usuarios por productos asignados a la empresa"),
    OLD_PASSWORD_001_ERROR("No se puede utilizar ninguna de las 2 últimas " +
            "contraseñas utilizadas con anterioridad"),
    OLD_PASSWORD_001("SUCCESS"),
    UPDATE_PASSWORD_001("La contraseña se actualizó con éxito"),
    UPDATE_PASSWORD_001_ERROR_2("Contraseña ingresada no corresponde a la registrada en la base de datos"),
    UPDATE_PASSWORD_001_ERROR_3("registre una contraseña diferente a las 3 anteriores registradas"),
    LIST_INF_USER_001("Informacion del usuario"),
    LIST_INF_USER_001_ERROR("Error al listar la Informacion del usuario"),
    M48("M48"),
    REMAINING_LICENSE_001_SUCCESS("success"),
    SAVE_USER_001("Usuario creado correctamente"),
    UPDATE_USER_001("Usuario modificado correctamente"),
    SAVE_USER_001_ERROR("El sistema no responde o el proceso no se terminó satisfactoriamente" +
            " intente nuevamente en unos segundos o comuníquese con soporte"),
    USER_ROLE_000("Los productos del rol no coinciden con los productos de la empresa seleccionada"),
    USER_ROLE_001("La empresa seleccionada no cuenta con usuarios disponibles"),
    USER_ROLE_002("Rol asociado correctamente"),
    USER_ACTIVE_LICENCES_001("Lista de usuarios contratados y disponibles por empresa y producto"),
    USER_ACTIVE_LICENCES_ERROR_001("Lista de usuarios contratados y disponibles por empresa y producto"),
    RESTORE_PASSWORD_000("El usuario no existe"),
    RESTORE_PASSWORD_001("Se ha enviado el enlace de verificacion al correo"),
    RESTORE_PASSWORD_002("El usuario no puede hacer mas de 2 solicitudes de cambio por dia"),
    LIST_COMPANY_ERROR("Error al listar las companias"),
    LIST_CONTACT_COMPANY("Error al listar las companias"),
    LIST_CONTACT_COMPANY_01("Error al listar las informacion de contacto de la compañia"),
    TIME_ZONE_USER_001("Lista de la zona horaria del usuario"),
    UPDATE_TIME_ZONE_USER_001("Zona horaria actualizada con exito"),
    RESEND_EMAIL_SUCCESS("Correo enviado Correctamente"),
    LIST_COMPANY_BY_USER_001("lista de empresas a las que pertenece el usuario"),
    PARAMETER_001("Proceso ejecutado correctamente"),
    SUCCESS("Proceso ejecutado correctamente"),
    GET_USER_PROFILE_001("Información del perfil del usuario"),
    GET_USER_PROFILE_002("No se encuentra la información del perfil del usuario"),
    TOKEN_ERROR_001("El token es invalido"),
    CREATE_COMPANY_ERROR("Error al crear la empresa"),
    COMPANY_X_PRODUCT_ERROR("Se ha presentado un error al asociar el producto con la empresa"),
    COMPANY_X_SUBCATEGORY_ERROR("Se ha presentado un error al asociar la categoria con la empresa"),
    CREATE_IDENTIFICATION_TYPE_ERROR("Error al crear el tipo de identificación"),
    LIST_OPTIONS_MENU_ERROR("Error al cargar las opciones de menu"),
    LIST_PRODUCTS_ERROR("Error al listar la lista de productos"),

    FIND_MODULE_ERROR("Error al buscar el producto"),
    TRUE("TRUE"),
    LOGIN_007(
            "Actualmente existe una sesión \" +\n"
                    + "            \"activa para este usuario, \" +\n"
                    + "            \"por favor inténtalo de nuevo en unos minutos o cierra la \" +\n"
                    + "            \"sesión activa presionando el botón continuar"),
    VERIFICATION_CODE_RESET_001(
            "El enlace de verificación no está vigente. " +
                    "Por favor realiza nuevamente el proceso de recordar tu contraseña"),
    VERIFICATION_CODE_ACTIVATE_001(
            "El enlace de verificación no está vigente. " +
                    "Por favor comunícate con soporte para que reenvíe tu correo de activación"),
    VERIFICATION_CODE_002("El código de verificación es incorrecto.");


    private String message;

    public String getMessage() {
        return message;
    }

}
