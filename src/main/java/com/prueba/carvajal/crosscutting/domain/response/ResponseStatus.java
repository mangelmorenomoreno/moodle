package com.prueba.carvajal.crosscutting.domain.response;


import com.prueba.carvajal.crosscutting.domain.enums.ResponseStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ResponseStatus
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStatus {
    private ResponseStatusCode statusCode;
    private String message;
}
