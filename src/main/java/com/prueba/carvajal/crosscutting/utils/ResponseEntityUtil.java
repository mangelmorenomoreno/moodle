package com.prueba.carvajal.crosscutting.utils;


import com.prueba.carvajal.crosscutting.domain.constants.ResponseValueConstants;
import com.prueba.carvajal.crosscutting.domain.dto.error.ValidationError;
import com.prueba.carvajal.crosscutting.domain.enums.ResponseStatusCode;
import com.prueba.carvajal.crosscutting.domain.response.ResponseStatus;
import com.prueba.carvajal.crosscutting.domain.response.RestResponse;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * ResponseEntityUtil.
 * This utility class is responsible for various
 * operations related to ResponseEntity objects.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseEntityUtil {
  public static <T> ResponseEntity<com.prueba.carvajal
                                     .crosscutting.patterns.IrestResponse<T>>
      createResponseEntity(
        final com.prueba.carvajal.crosscutting.patterns.IrestResponse<T> response) {
    return ResponseEntity.status(HttpStatus.valueOf(response.getHttpStatusCode())).body(response);
  }

  /**
   * Crea un ResponseEntity que encapsula una
   * respuesta para un error de validación.
   * Este método toma un objeto ValidationError,
   * lo procesa y construye un RestResponse
   * con los detalles relevantes del error y el código de estado HTTP.
   *
   * @param error El objeto ValidationError que contiene
   *              detalles sobre el error de validación.
   * @param <T>   El tipo de respuesta esperada.
   * @return ResponseEntity de tipo IrestResponse que contiene
   */
  public static <T> ResponseEntity<com.prueba.carvajal.crosscutting.patterns.IrestResponse<T>>
      createResponseValidationError(final ValidationError error) {
    final RestResponse<T> fullResponse = new RestResponse<>();

    if (!Objects.isNull(error)) {
      final ResponseStatus responseStatus = getErrorResponseStatus(ResponseValueConstants.ERROR);

      fullResponse.setResponseStatus(responseStatus);
      fullResponse.setHttpStatusCode(HttpStatus.OK.value());
      fullResponse.setDetail(error.getDetail());
      fullResponse.setTranslationCode(error.getTranslationCode());
    }

    return createResponseEntity(fullResponse);
  }

  /**
   * Crea una ResponseEntity que encapsula una respuesta exitosa.
   * Este método toma un mensaje, datos, detalles y
   * un código de traducción, y construye una RestResponse
   * con los detalles de éxito y el código de estado
   * HTTP correspondiente.
   *
   * @param message         El mensaje que describe el
   *                        éxito de la operación.
   * @param data            Los datos a incluir en la
   *                        respuesta, del tipo especificado por T.
   * @param detail          Detalles adicionales sobre
   *                        la respuesta.
   * @param translationCode Código de traducción para
   *                        mensajes de internacionalización.
   * @param <T>             El tipo de los datos que se
   *                        esperan en la respuesta.
   * @return ResponseEntity del tipo IrestResponse que
   */
  public static <T> ResponseEntity<com.prueba.carvajal.crosscutting.patterns.IrestResponse<T>>
        createSuccessfulResponse(final String message, final T data, final String detail,
                                 final String translationCode) {
    final ResponseStatus status = getSuccessResponseStatus(message);

    final RestResponse<T> fullResponse = new RestResponse<>();
    fullResponse.setResponseStatus(status);
    fullResponse.setHttpStatusCode(ResponseStatusCode.OK.getcode());
    fullResponse.setData(data);
    fullResponse.setDetail(detail);
    fullResponse.setTranslationCode(translationCode);

    return createResponseEntity(fullResponse);
  }

  /**
   * Crea una ResponseEntity que encapsula una respuesta exitosa
   * con soporte multilingüe.
   * Este método construye una respuesta que incluye un mensaje
   * de éxito, datos, detalles, un código de traducción,
   * y el idioma específico de la respuesta. Ideal para crear
   * respuestas estandarizadas con contenido internacionalizado.
   *
   * @param message         El mensaje que describe el éxito de la operación.
   * @param data            Los datos a incluir en la respuesta,
   *                        del tipo especificado por T.
   * @param detail          Detalles adicionales sobre la respuesta.
   * @param translationCode Código de traducción para mensajes,
   *                        permitiendo la internacionalización.
   * @param lang            El idioma en el que se presenta la respuesta.
   * @param <T>             El tipo de los datos que se esperan
   *                        en la respuesta.
   * @return ResponseEntity del tipo IrestResponse que contiene
   */
  public static <T> ResponseEntity<com.prueba.carvajal.crosscutting.patterns.IrestResponse<T>>
        createSuccessfulResponse(final String message, final T data, final String detail,
                                 final String translationCode, final String lang) {
    final ResponseStatus status = getSuccessResponseStatus(message);
    final RestResponse<T> fullResponse = new RestResponse<>();
    fullResponse.setResponseStatus(status);
    fullResponse.setHttpStatusCode(ResponseStatusCode.OK.getcode());
    fullResponse.setData(data);
    fullResponse.setDetail(detail);
    fullResponse.setTranslationCode(translationCode);
    fullResponse.setLang(lang);
    return createResponseEntity(fullResponse);

  }

  /**
   * Crea una ResponseEntity que encapsula una respuesta exitosa
   * para una operación sin datos de retorno específicos.
   * Este método construye una respuesta que incluye un mensaje de
   * éxito, detalles, un código de traducción,
   * y el idioma específico de la respuesta. Es útil para situaciones
   * donde solo se necesita confirmar el éxito
   * de una operación sin enviar datos adicionales.
   *
   * @param message         El mensaje que describe el éxito de la operación.
   * @param detail          Detalles adicionales sobre la operación realizada.
   * @param translationCode Código de traducción para mensajes, permitiendo la internacionalización.
   * @param lang            El idioma en el que se presenta la respuesta.
   * @param <T>             El tipo genérico, que permite flexibilidad en
   *                        el tipo de respuesta (puede no usarse en este caso específico).
   * @return ResponseEntity del tipo IrestResponse que contiene los
   */
  public static <T> ResponseEntity<com.prueba.carvajal.crosscutting.patterns.IrestResponse<T>>
        createSuccessfulResponse(final String message,
                                 final String detail,
                                 final String translationCode, final String lang) {
    final ResponseStatus status = getSuccessResponseStatus(message);

    final RestResponse<T> fullResponse = new RestResponse<>();
    fullResponse.setResponseStatus(status);
    fullResponse.setHttpStatusCode(ResponseStatusCode.OK.getcode());
    fullResponse.setDetail(detail);
    fullResponse.setTranslationCode(translationCode);
    fullResponse.setLang(lang);
    return createResponseEntity(fullResponse);
  }

  /**
   * Crea una ResponseEntity que encapsula una respuesta exitosa para una
   * operación sin datos de retorno específicos.
   * Este método construye una respuesta que incluye un mensaje de éxito,
   * detalles, un código de traducción,
   * y el idioma específico de la respuesta. Es útil para situaciones
   * donde solo se necesita confirmar el éxito
   * de una operación sin enviar datos adicionales.
   *
   * @param message         El mensaje que describe el éxito
   *                        de la operación.
   * @param detail          Detalles adicionales sobre la
   *                        operación realizada.
   * @param translationCode Código de traducción para mensajes,
   *                        permitiendo la internacionalización.
   *                        presenta la respuesta.
   * @param <T>             El tipo genérico, que permite flexibilidad en el
   *                        tipo de respuesta (puede no usarse en este caso específico).
   * @return ResponseEntity del tipo IrestResponse que contiene los detalles
   */
  public static <T> ResponseEntity<com.prueba.carvajal.crosscutting.patterns.IrestResponse<T>>
        createSuccessfulResponse(final String message,
                                 final String detail, final String translationCode) {
    final ResponseStatus status = getSuccessResponseStatus(message);

    final RestResponse<T> fullResponse = new RestResponse<>();
    fullResponse.setResponseStatus(status);
    fullResponse.setHttpStatusCode(ResponseStatusCode.OK.getcode());
    fullResponse.setDetail(detail);
    fullResponse.setTranslationCode(translationCode);
    return createResponseEntity(fullResponse);
  }

  /**
   * Creates a ResponseEntity encapsulating a successful response
   * with specified message, data, and details.
   * This method constructs a RestResponse with a success status,
   * the provided data, and additional details about the success.
   * It's useful for standardizing responses across different
   * parts of the application.
   *
   * @param message The success message that describes the
   *                outcome of the operation.
   * @param data    The data to be included in the response.
   *                This is generic and can be of any type specified by T.
   * @param detail  Additional details about the successful operation.
   * @param <T>     The type of the data expected in the response.
   * @return ResponseEntity of type IrestResponse
   */
  public static <T> ResponseEntity<com.prueba.carvajal.crosscutting.patterns.IrestResponse<T>>
        createSuccessfulResponse(final String message,
                                 final T data,
                                 final String detail) {
    final ResponseStatus status = getSuccessResponseStatus(message);
    final RestResponse<T> fullResponse = new RestResponse<>();
    fullResponse.setResponseStatus(status);
    fullResponse.setHttpStatusCode(ResponseStatusCode.OK.getcode());
    fullResponse.setData(data);
    fullResponse.setDetail(detail);

    return createResponseEntity(fullResponse);
  }

  /**
   * Crea una respuesta exitosa de tipo {@link ResponseEntity} envolviendo un mensaje personalizado
   * y datos.
   * Este método es útil para estandarizar las respuestas exitosas en APIs REST, encapsulando
   * la información
   * en un objeto {@link com.prueba.carvajal.crosscutting.patterns.IrestResponse}.
   * Configura el estado de respuesta como exitoso con un mensaje personalizado
   * y asigna los datos proporcionados. Además, establece el código de estado HTTP como OK (200).
   *
   * @param <T> El tipo genérico del dato que se va a incluir en la respuesta.
   * @param message El mensaje personalizado para incluir en la respuesta exitosa.
   * @param data Los datos de tipo T que se incluirán en la respuesta.
   * @return Un objeto {@link ResponseEntity} que contiene el estado de la respuesta,
   *         el código de estado HTTP y los datos proporcionados.
   */
  public static <T> ResponseEntity<com.prueba.carvajal.crosscutting.patterns.IrestResponse<T>>
        createSuccessfulResponse(final String message,
                                 final T data) {
    final ResponseStatus status = getSuccessResponseStatus(message);

    final RestResponse<T> fullResponse = new RestResponse<>();
    fullResponse.setResponseStatus(status);
    fullResponse.setHttpStatusCode(ResponseStatusCode.OK.getcode());
    fullResponse.setData(data);

    return createResponseEntity(fullResponse);
  }

  private static ResponseStatus getErrorResponseStatus(final String message) {
    return ResponseStatus.builder()
             .message(message)
             .statusCode(ResponseStatusCode.OK)
             .build();
  }

  private static ResponseStatus getSuccessResponseStatus(final String message) {
    return ResponseStatus.builder().message(message).statusCode(ResponseStatusCode.OK).build();
  }
}
