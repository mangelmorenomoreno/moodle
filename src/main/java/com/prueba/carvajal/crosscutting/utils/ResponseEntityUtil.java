package com.prueba.carvajal.crosscutting.utils;


import com.prueba.carvajal.crosscutting.domain.constants.ResponseValueConstants;
import com.prueba.carvajal.crosscutting.domain.dto.error.ValidationError;
import com.prueba.carvajal.crosscutting.domain.enums.ResponseStatusCode;
import com.prueba.carvajal.crosscutting.domain.response.ResponseStatus;
import com.prueba.carvajal.crosscutting.domain.response.RestResponse;
import com.prueba.carvajal.crosscutting.patterns.IRestResponse;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * ResponseEntityUtil
 *
 * @author miguel.moreno
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseEntityUtil {
    public static <T> ResponseEntity<IRestResponse<T>> createResponseEntity(
            final IRestResponse<T> response) {
        return ResponseEntity.status(HttpStatus.valueOf(response.getHttpStatusCode())).body(response);
    }

    public static <T> ResponseEntity<IRestResponse<T>> createResponseValidationError(
            final ValidationError error) {
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

    public static <T> ResponseEntity<IRestResponse<T>> createSuccessfulResponse(
            final String message, final T data, final String detail, final String translationCode) {
        final ResponseStatus status = getSuccessResponseStatus(message);

        final RestResponse<T> fullResponse = new RestResponse<>();
        fullResponse.setResponseStatus(status);
        fullResponse.setHttpStatusCode(ResponseStatusCode.OK.getcode());
        fullResponse.setData(data);
        fullResponse.setDetail(detail);
        fullResponse.setTranslationCode(translationCode);

        return createResponseEntity(fullResponse);
    }


    public static <T> ResponseEntity<IRestResponse<T>> createSuccessfulResponse(
            final String message,
            final T data,
            final String detail,
            final String translationCode,
            final String lang) {
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

    public static <T> ResponseEntity<IRestResponse<T>> createSuccessfulResponse(
            final String message, final String detail, final String translationCode, final String lang) {
        final ResponseStatus status = getSuccessResponseStatus(message);

        final RestResponse<T> fullResponse = new RestResponse<>();
        fullResponse.setResponseStatus(status);
        fullResponse.setHttpStatusCode(ResponseStatusCode.OK.getcode());
        fullResponse.setDetail(detail);
        fullResponse.setTranslationCode(translationCode);
        fullResponse.setLang(lang);
        return createResponseEntity(fullResponse);
    }

    public static <T> ResponseEntity<IRestResponse<T>> createSuccessfulResponse(
            final String message, final String detail, final String translationCode) {
        final ResponseStatus status = getSuccessResponseStatus(message);

        final RestResponse<T> fullResponse = new RestResponse<>();
        fullResponse.setResponseStatus(status);
        fullResponse.setHttpStatusCode(ResponseStatusCode.OK.getcode());
        fullResponse.setDetail(detail);
        fullResponse.setTranslationCode(translationCode);
        return createResponseEntity(fullResponse);
    }

    public static <T> ResponseEntity<IRestResponse<T>> createSuccessfulResponse(
            final String message, final T data, final String detail) {
        final ResponseStatus status = getSuccessResponseStatus(message);

        final RestResponse<T> fullResponse = new RestResponse<>();
        fullResponse.setResponseStatus(status);
        fullResponse.setHttpStatusCode(ResponseStatusCode.OK.getcode());
        fullResponse.setData(data);
        fullResponse.setDetail(detail);

        return createResponseEntity(fullResponse);
    }

    public static <T> ResponseEntity<IRestResponse<T>> createSuccessfulResponse(
            final String message, final T data) {
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
