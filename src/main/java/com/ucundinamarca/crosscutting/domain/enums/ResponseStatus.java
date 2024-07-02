package com.ucundinamarca.crosscutting.domain.enums;

/**
 * ResponseStatus.
 *
 * @author miguel.moreno
 * @since 2024-03-06
 */
public enum ResponseStatus {
  OK(true, "true"),
  ERROR_CREAR_PASSWORD_01(false, "Usuario y contraseña ya han sido usados"),
  ERROR_CREAR_PASSWORD_02(false, "Usuario y contraseña ya han sido usados");


  private Boolean status;
  private String desc;

  ResponseStatus(Boolean status, String desc) {
    this.status = status;
    this.desc = desc;
  }

  /**
   * gets the http status code.
   *
   * @return the status code number
   */
  public Boolean getStatus() {
    return status;
  }

  /**
   * get the description.
   *
   * @return the description of the status code
   */
  public String getdesc() {
    return desc;
  }
}
