package com.ucundinamarca.crosscutting.domain.enums;

/**
 * ResponseStatusCode.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
public enum ResponseStatusCode {
  OK(200, "ok");


  private final int code;
  private final String desc;

  ResponseStatusCode(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  /**
   * gets the http status code.
   *
   * @return the status code number
   */
  public int getcode() {
    return code;
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
