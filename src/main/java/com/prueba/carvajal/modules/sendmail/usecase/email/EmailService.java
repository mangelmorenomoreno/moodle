package com.prueba.carvajal.modules.sendmail.usecase.email;

import com.prueba.carvajal.crosscutting.domain.constants.ApiDocumentationConstant;
import com.prueba.carvajal.crosscutting.persistence.entity.Credencial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Value("${spring.mail.username}")
  private String emailFrom;

  @Autowired
  private JavaMailSender mailSender;

  public void sendActivateEmail(Credencial credencial) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(emailFrom);
    message.setTo(credencial.getUsuario().getCorreoElectronico());
    message.setSubject(ApiDocumentationConstant.ACTIVATE_USER);
    message.setText(crearContenidoCorreoActivacion(credencial));
    mailSender.send(message);
  }

  public void sendRecuperarPassword(Credencial credencial) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(emailFrom);
    message.setTo(credencial.getUsuario().getCorreoElectronico());
    message.setSubject(ApiDocumentationConstant.ACTIVATE_USER);
    message.setText(crearContenidoCorreoRecuperacionContraseña(credencial));
    mailSender.send(message);
  }

  private String crearContenidoCorreoActivacion(Credencial credencial) {
    String contenido = "Hola " + credencial.getUsuario().getNombre() + " " + credencial.getUsuario().getApellido() + ",\n\n" +
        "¡Gracias por registrarte en nuestra plataforma! Estás a solo un paso de activar tu cuenta y comenzar a usar nuestros servicios.\n\n" +
        "Por favor, activa tu cuenta haciendo clic en el siguiente enlace:\n" +
        credencial.getTokenResetPassword() + "\n\n" +
        "Si tienes alguna pregunta, no dudes en contactarnos en "+emailFrom+".\n\n" +
        "Si no te registraste en nuestra plataforma, por favor ignora este mensaje.\n\n" +
        "Gracias,\n" +
        "El equipo de Carvajal";
    return contenido;
  }

  public String crearContenidoCorreoRecuperacionContraseña(Credencial credencial) {
    String contenido = "Hola " +  credencial.getUsuario().getNombre() + " " + credencial.getUsuario().getApellido() + ",\n\n" +
        "Hemos recibido una solicitud para restablecer tu contraseña. Si no solicitaste esto, por favor ignora este correo. De lo contrario, puedes restablecer tu contraseña utilizando el siguiente enlace:\n\n" +
        credencial.getTokenResetPassword() + "\n\n" +
        "Este enlace solo será válido por un período limitado de tiempo.\n\n" +
        "Si tienes problemas para restablecer tu contraseña o no solicitaste este cambio, por favor contacta a nuestro equipo de soporte en [tu-email-de-soporte@example.com].\n\n" +
        "Saludos,\n" +
        "El equipo de [Nombre de tu Empresa o Aplicación]";
    return contenido;
  }
}