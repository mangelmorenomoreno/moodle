package com.prueba.carvajal.modules.sendmail.usecase.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucundinamarca.crosscutting.domain.dto.user.UserModelMacro;
import com.ucundinamarca.modules.credencial.dataproviders.IcredencialDataProvider;
import com.ucundinamarca.modules.sendmail.usecase.email.EmailService;
import com.ucundinamarca.modules.sendmail.usecase.message.MessageSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

/**
 * MessageSenderServiceTest.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@RunWith(MockitoJUnitRunner.class)
public  class MessageSenderServiceTest  {


  @InjectMocks
  private MessageSenderService messageSenderService;

  @Mock
  private JmsTemplate jmsTemplate;

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private EmailService emailService;

  @Mock
  private IcredencialDataProvider icredencialDataProvider;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void sendMessageTest() throws Exception {
    String destination = "testQueue";
    UserModelMacro userModelMacro = new UserModelMacro(); // Asume setters apropiados
    String serializedData = "{\"test\": \"data\"}";

    when(objectMapper.writeValueAsString(userModelMacro)).thenReturn(serializedData);

    messageSenderService.sendMessage(destination, userModelMacro);

    verify(jmsTemplate).convertAndSend(destination, serializedData);
  }

  @Test
  public void serializeUserModelMacroTest() throws JsonProcessingException {
    UserModelMacro userModelMacro = new UserModelMacro();
    String expectedJson = "{\"test\": \"data\"}";

    when(objectMapper.writeValueAsString(userModelMacro)).thenReturn(expectedJson);

    String result = messageSenderService.serializeUserModelMacro(userModelMacro);

    assertEquals(expectedJson, result);
  }



}