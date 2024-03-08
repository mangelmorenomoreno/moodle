package com.prueba.carvajal.infrastructure.configuration;


import java.util.Collections;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Clase de configuración para JMS en Spring Boot.
 * Esta clase configura la conexión a ActiveMQ y define los beans necesarios para
 * el manejo de mensajes JMS.
 *
 * @author carvajal
 * @version 1.0
 * @since 2020-04-21
 */

@Configuration
public class JmsConfig {

  @Value("${integration.amq.url}")
  private String brokerUrl;

  @Value("${integration.amq.user}")
  private String brokerUsername;

  @Value("${integration.amq.password}")
  private String brokerPassword;

  @Value("${integration.amq.concurrentConsumers}")
  private String jmsConcurrency;

  /**
   * Crea y configura una fábrica de conexiones para ActiveMQ.
   *
   * @return una instancia de ActiveMQConnectionFactory configurada.
   */
  @Bean
  public ActiveMQConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
        brokerUsername,
        brokerPassword,
        brokerUrl
    );
    connectionFactory.setTrustedPackages(
        Collections.singletonList(
            "co.com.carvajal.platform.messaging"
        ));
    return connectionFactory;
  }

  /**
   * Crea y configura un JmsTemplate para operaciones JMS.
   *
   * @return una instancia de JmsTemplate configurada.
   */
  @Bean("jmsTemplate")
  public JmsOperations jmsTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate();
    jmsTemplate.setConnectionFactory(connectionFactory());
    return jmsTemplate;
  }

  /**
   * Crea y configura una fábrica de contenedores de listeners para JMS.
   *
   * @param configurer un configurador para la fábrica de contenedores de listeners.
   * @return una instancia de DefaultJmsListenerContainerFactory configurada.
   */
  @Bean
  public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(
      DefaultJmsListenerContainerFactoryConfigurer configurer
  ) {
    DefaultJmsListenerContainerFactory factory =
        new DefaultJmsListenerContainerFactory();
    configurer.configure(factory, connectionFactory());
    factory.setMessageConverter(messageConverter());
    return factory;
  }

  /**
   * Crea y configura un convertidor de mensajes para JMS.
   *
   * @return una instancia de MessageConverter.
   */
  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter =
        new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.OBJECT);
    return converter;
  }


}

