package com.ucundinamarca.modules.sendmail.usecase.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucundinamarca.crosscutting.domain.dto.user.UserModelMacro;
import com.ucundinamarca.modules.sendmail.usecase.email.EmailService;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
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


  private UserModelMacro deserializeMessage(TextMessage textMessage)
      throws IOException, JMSException {
    String jsonPayload = textMessage.getText();
    return objectMapper.readValue(jsonPayload, UserModelMacro.class);
  }


}
