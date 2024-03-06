package com.prueba.carvajal.crosscutting.domain.constants;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ApiDocumentationConstant.
 *
 * @author miguel.moreno
 * @since 2024-03-06
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiDocumentationConstant {


  public static final String CONSULT = "consult";
  public static final String UPDATE_TRANSACTION_OBJECT = "Update Transaction object";
  public static final String UPDATE_EXISTING_TRANSACTION = "Update existing Transaction";
  public static final String SUCCESS = "success";
  public static final String ERROR = "error";

}