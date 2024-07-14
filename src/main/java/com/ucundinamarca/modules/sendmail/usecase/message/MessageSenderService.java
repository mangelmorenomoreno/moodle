package com.ucundinamarca.modules.sendmail.usecase.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucundinamarca.crosscutting.domain.dto.user.UserModelMacro;
import com.ucundinamarca.modules.sendmail.usecase.email.EmailService;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import java.io.IOException;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * EmailService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@Service
public class MessageSenderService {


  @Autowired
  private JmsTemplate jmsTemplates;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EmailService emailService;

  public void sendMessage(String destination, UserModelMacro userModelMacro) {
    jmsTemplates.convertAndSend(destination, serializeUserModelMacro(userModelMacro));
  }

  /**
   * Convierte un objeto UserModelMacro en su representación de cadena en formato JSON.
   * Utiliza el objeto objectMapper para la serialización. Si ocurre una excepción durante el
   * proceso, se lanza una RuntimeException.
   *
   * @param userModelMacro El objeto UserModelMacro a ser serializado.
   * @return Una cadena que representa al objeto UserModelMacro en formato JSON.
   * @throws RuntimeException Si se produce un error durante la serialización.
   */
  public String serializeUserModelMacro(UserModelMacro userModelMacro) {
    try {
      return objectMapper.writeValueAsString(userModelMacro);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error al serializar UserModelMacro", e);
    }
  }

  /**
   * Escucha los mensajes en la cola JMS destinada a la activación de usuarios y realiza el
   * proceso correspondiente. Si el mensaje es del tipo TextMessage, deserializa el contenido a un
   * objeto UserModelMacro y procede a enviar un correo electrónico de activación utilizando el
   * servicio de correo electrónico.
   *
   * @param message El mensaje JMS recibido de la cola de activación.
   * @throws JMSException Si ocurre un problema al manejar el mensaje JMS.
   * @throws IOException Si se produce un error durante la deserialización del mensaje.
   */
  /*@JmsListener(destination = "${integration.queues.activate}")
  public void activateUser(Message message) throws JMSException, IOException {
    if (message instanceof TextMessage) {
      UserModelMacro userModelMacro = deserializeMessage((TextMessage) message);
      emailService.sendActivateEmail(
          icredencialDataProvider.findByUserId(userModelMacro.getUserId())
      );
    }
  }

   */

  /**
   * Maneja los mensajes JMS en la cola destinada a la recuperación de contraseñas. Al recibir un
   * mensaje del tipo TextMessage, lo deserializa para obtener un objeto UserModelMacro y utiliza
   * este para invocar el envío de un correo electrónico para la recuperación de contraseña a través
   * del servicio de correo electrónico.
   *
   * @param message El mensaje JMS recibido de la cola de recuperación de contraseña.
   * @throws JMSException Si se produce un error en el manejo del mensaje JMS.
   * @throws IOException Si se produce un error durante la deserialización del mensaje.
   */
  /*

  @JmsListener(destination = "${integration.queues.recuperarPassword}")
  public void enviarEmailRecuperar(Message message) throws JMSException, IOException {
    if (message instanceof TextMessage) {
      UserModelMacro userModelMacro = deserializeMessage((TextMessage) message);
      emailService.sendRecuperarPassword(
          icredencialDataProvider.findByUserId(userModelMacro.getUserId())
      );
    }
  }

   */

  private UserModelMacro deserializeMessage(TextMessage textMessage)
      throws IOException, JMSException {
    String jsonPayload = textMessage.getText();
    return objectMapper.readValue(jsonPayload, UserModelMacro.class);
  }


}
