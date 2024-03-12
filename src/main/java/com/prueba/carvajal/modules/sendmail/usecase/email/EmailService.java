package com.prueba.carvajal.modules.sendmail.usecase.email;

import com.prueba.carvajal.crosscutting.domain.constants.ApiDocumentationConstant;
import com.prueba.carvajal.crosscutting.persistence.entity.Credencial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * EmailService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Service
public class EmailService {

  @Value("${spring.mail.username}")
  private String emailFrom;

  @Autowired
  private JavaMailSender mailSender;

  /**
   * Envía un correo electrónico de activación al usuario.
   * Este método crea y envía un correo electrónico simple para activar la cuenta de un usuario.
   * El mensaje incluye un enlace para la activación de la cuenta, construido a partir del token
   * de restablecimiento
   * de contraseña almacenado en la credencial del usuario.
   *
   * @param credencial La credencial del usuario a quien se le enviará el correo de activación.
   */
  public void sendActivateEmail(Credencial credencial) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(emailFrom);
    message.setTo(credencial.getUsuario().getCorreoElectronico());
    message.setSubject(ApiDocumentationConstant.ACTIVATE_USER);
    message.setText(crearContenidoCorreoActivacion(credencial));
    mailSender.send(message);
  }

  /**
   * Envía un correo electrónico para la recuperación de contraseña al usuario.
   * Este método prepara y envía un mensaje de correo electrónico con instrucciones y un enlace
   * para restablecer la contraseña del usuario. El enlace incluye un token de restablecimiento
   * de contraseña específico para el usuario.
   *
   * @param credencial La credencial del usuario que necesita recuperar su contraseña.
   */
  public void sendRecuperarPassword(Credencial credencial) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(emailFrom);
    message.setTo(credencial.getUsuario().getCorreoElectronico());
    message.setSubject(ApiDocumentationConstant.ACTIVATE_USER);
    message.setText(crearContenidoCorreoRecuperacionContrasena(credencial));
    mailSender.send(message);
  }

  private String crearContenidoCorreoActivacion(Credencial credencial) {
    return "Hola " + credencial.getUsuario().getNombre() + " "
             + credencial.getUsuario().getApellido() + ",\n\n"
             + "¡Gracias por registrarte en nuestra plataforma! Estás a solo un paso de activar tu"
             + " cuenta y comenzar a usar nuestros servicios.\n\n"
             + "Por favor, activa tu cuenta haciendo clic en el siguiente enlace:  \n"
             + "http://localhost:4200/reset-password?token=" + credencial.getTokenResetPassword()
             + " Si tienes alguna pregunta, no dudes en contactarnos en " + emailFrom + ".\n\n"
             + "Si no te registraste en nuestra plataforma, por favor ignora este mensaje.\n\n"
             + "Gracias,\n"
             + "El equipo de Carvajal";
  }

  /**
   * Crea el contenido del correo electrónico de activación de cuenta.
   * Este método genera el texto del mensaje de correo electrónico para activar la cuenta de un
   * usuario.
   * Incluye un saludo personalizado, instrucciones para la activación y un enlace de activación
   * con el token de restablecimiento de contraseña del usuario.
   *
   * @param credencial La credencial del usuario para el cual se está creando el contenido del
   *                   correo.
   * @return Un String con el texto completo del mensaje de correo electrónico de activación.
   */
  public String crearContenidoCorreoRecuperacionContrasena(Credencial credencial) {
    return "Hola " + credencial.getUsuario().getNombre() + " "
             + credencial.getUsuario().getApellido() + ",\n\n"
             + "Hemos recibido una solicitud para restablecer tu contraseña."
             + " Si no solicitaste esto, "
             + "por favor ignora este correo. De lo contrario, puedes restablecer tu contraseña "
             + "utilizando el siguiente enlace:\n\n"
             + "http://localhost:4200/reset-password?token=" + credencial.getTokenResetPassword()
             + " Este enlace solo será válido por un período limitado de tiempo.\n\n"
             + " Si tienes problemas para restablecer tu contraseña o no solicitaste este cambio, "
             + "por favor contacta a nuestro equipo de soporte "
             + "Saludos,\n"
             + "El equipo de Carvajal";
  }
}