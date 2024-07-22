package com.ucundinamarca.crosscutting.patterns;


import com.ucundinamarca.crosscutting.domain.response.ResponseStatus;

/**
 * IrestResponse.
 *
 * @param <T> The expected class of the value
 * @author miguel.moreno
 * @version 1.0
 */
public interface IrestResponse<T> {
  ResponseStatus getResponseStatus();

  T getData();

  int getHttpStatusCode();

  String getDetail();

  String getTranslationCode();

  String getLang();
}
