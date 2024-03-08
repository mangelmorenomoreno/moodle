package com.prueba.carvajal.modules.sendmail.usecase.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.carvajal.crosscutting.domain.dto.user.UserModelMacro;
import com.prueba.carvajal.modules.credencial.dataproviders.ICredencialDataProvider;
import com.prueba.carvajal.modules.sendmail.usecase.email.EmailService;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import java.io.IOException;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {


  private JmsTemplate jmsTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EmailService emailService;

  @Autowired
  private ICredencialDataProvider iCredencialDataProvider;

  public void sendMessage(String destination, UserModelMacro userModelMacro) {
    jmsTemplate.convertAndSend(destination, serializeUserModelMacro(userModelMacro));
  }


  public String serializeUserModelMacro(UserModelMacro userModelMacro) {
    try {
      return objectMapper.writeValueAsString(userModelMacro);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error al serializar UserModelMacro", e);
    }
  }

  @JmsListener(destination = "${integration.queues.activate}")
  public void activateUser(Message message) throws JMSException, IOException {
    if (message instanceof TextMessage) {
      UserModelMacro userModelMacro = deserializeMessage((TextMessage) message);
      emailService.sendActivateEmail(
          iCredencialDataProvider.findByUserId(userModelMacro.getUserId())
      );
    }
  }


  @JmsListener(destination = "${integration.queues.recuperarPassword}")
  public void enviarEmailRecuperar(Message message) throws JMSException, IOException {
    if (message instanceof TextMessage) {
      UserModelMacro userModelMacro = deserializeMessage((TextMessage) message);
      emailService.sendRecuperarPassword(
          iCredencialDataProvider.findByUserId(userModelMacro.getUserId())
      );
    }
  }

  private UserModelMacro deserializeMessage(TextMessage textMessage)
      throws IOException, JMSException {
    String jsonPayload = textMessage.getText();
    return objectMapper.readValue(jsonPayload, UserModelMacro.class);
  }


}
