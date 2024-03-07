package com.prueba.carvajal.crosscutting.domain.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prueba.carvajal.crosscutting.patterns.IRestResponse;
import lombok.Setter;

/**
 * RestResponse
 *
 * @param <T> The expected class of the value
 * @author miguel.moreno
 * @version 1.0
 */
@Setter
public class RestResponse<T> implements IRestResponse<T> {
    private ResponseStatus responseStatus;
    private T data;
    private String detail;
    private String lang;

    @JsonProperty("TranslationCode")
    private String translationCode;

    @JsonIgnore
    private int httpStatusCode;

    @Override
    public ResponseStatus getResponseStatus() {
        return this.responseStatus;
    }

    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public int getHttpStatusCode() {
        return this.httpStatusCode;
    }

    @Override
    public String getDetail() {
        return this.detail;
    }

    @Override
    public String getTranslationCode() {
        return this.translationCode;
    }

    @Override
    public String getLang() {
        return this.lang;
    }
}
