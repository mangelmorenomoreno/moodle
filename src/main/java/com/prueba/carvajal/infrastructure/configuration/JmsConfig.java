package com.prueba.carvajal.infrastructure.configuration;


import jakarta.jms.ConnectionFactory;
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

  public JmsOperations jmsTemplateOperations() {
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
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.OBJECT);
    return converter;
  }

  /**
   * Configura y proporciona un bean de {@link JmsTemplate} para la gestión de mensajes JMS.
   * Este método es marcado con la anotación @Bean, lo que indica que su retorno es un componente
   * de Spring que puede ser inyectado en otras partes de la aplicación. {@link JmsTemplate} es
   * utilizado para simplificar la creación y recepción de mensajes a través de JMS, y este método
   * configura la plantilla con una {@link ConnectionFactory} específica.
   *
   * @param connectionFactory La fábrica de conexiones que se utilizará para configurar la
   *                          {@link JmsTemplate}.
   * @return Un objeto {@link JmsTemplate} configurado, listo para ser utilizado en la aplicación.
   */
  @Bean
  public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory);
    return template;
  }
}

