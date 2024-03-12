package com.prueba.carvajal.crosscutting.patterns;


import com.prueba.carvajal.crosscutting.domain.response.ResponseStatus;

/**
 * IrestResponse.
 *
 * @author miguel.moreno
 * @version 1.0
 * @param <T> The expected class of the value
 */
public interface IrestResponse<T> {
  ResponseStatus getResponseStatus();

  T getData();

  int getHttpStatusCode();

  String getDetail();

  String getTranslationCode();

  String getLang();
}
