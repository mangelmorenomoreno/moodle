package com.prueba.carvajal.crosscutting.domain.enums;

/**
 * ResponseStatusCode
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
public enum ResponseStatusCode {
  SWITCHING_PROTOCOL(101, "switching protocols"),
  PROCESSING(102, "processing"),

  OK(200, "ok"),
  CREATED(201, "created"),
  ACCEPTED(202, "accepted"),
  NON_AUTHORITATIVE_INFORMATION(203, "non-authoritative information"),
  NO_CONTENT(204, "no content"),
  RESET_CONTENT(205, "reset content"),
  PARTIAL_CONTENT(206, "partial content"),
  MULTI_STATUS(207, "multi-status (webdav; rfc 4918"),
  ALREADY_REPORTED(208, "already reported (webdav; rfc 5842)"),
  IM_USED(226, "im used (rfc 3229)"),

  MULTIPLE_CHOICES(300, "multiple choices"),
  MOVED_PERMANENTLY(301, "moved permanently"),
  FOUND(302, "found"),
  SEE_OTHER(303, "see other (since http/1.1)"),
  NOT_MODIFIED(304, "not modified"),
  USE_PROXY(305, "use proxy (since http/1.1)"),
  SWITCH_PROXY(306, "switch proxy"),
  TEMPORARY_REDIRECT(307, "temporary redirect (since http/1.1)"),
  PERMANENT_REDIRECT(308, "permanent redirect (approved as experimental rfc)[12]"),

  BAD_REQUEST(400, "bad request"),
  UNAUTHORIZED(401, "unauthorized"),
  PAYMENT_REQUIRED(402, "payment required"),
  FORBIDDEN(403, "forbidden"),
  NOT_FOUND(404, "not found"),
  METHOD_NOT_ALLOWED(405, "method not allowed"),
  NOT_ACCEPTABLE(406, "not acceptable"),
  PROXY_AUTHENTICATION_REQUIRED(407, "proxy authentication required"),
  REQUEST_TIMEOUT(408, "request timeout"),
  CONFLICT(409, "conflict"),
  GONE(410, "gone"),
  LENGTH_REQUIRED(411, "length required"),
  PRECONDITION_FAILED(412, "precondition failed"),
  REQUEST_ENTITY_TOO_LARGE(413, "request entity too large"),
  REQUEST_URI_TOO_LONG(414, "request-uri too long"),
  UNSUPPORTED_MEDIA_TYPE(415, "unsupported media type"),
  REQUESTED_RANGE_NOT_SATISFIABLE(416, "requested range not satisfiable"),
  EXPECTATION_FAILED(417, "expectation failed"),

  INTERNAL_SERVER_ERROR(500, "internal server error"),
  NOT_IMPLEMENTED(501, "not implemented"),
  BAD_GATEWAY(502, "bad gateway"),
  SERVICE_UNAVAILABLE(503, "service unavailable"),
  GATEWAY_TIMEOUT(504, "gateway timeout"),
  HTTP_VERSION_NOT_SUPPORTED(505, "http version not supported"),
  VARIANT_ALSO_NEGOTIATES(506, "variant also negotiates (rfc 2295)"),
  INSUFFICIENT_STORAGE(507, "insufficient storage (webdav; rfc 4918)"),
  LOOP_DETECTED(508, "loop detected (webdav; rfc 5842)"),
  BANDWIDTH_LIMIT_EXCEEDED(509, "bandwidth limit exceeded (apache bw/limited extension)"),
  NOT_EXTEND(510, "not extended (rfc 2774)"),
  NETWORK_AUTHENTICATION_REQUIRED(511, "network authentication required (rfc 6585)"),
  CONNECTION_TIMED_OUT(522, "connection timed out"),
  PROXY_DECLINED_REQUEST(523, "proxy declined request"),
  TIMEOUT_OCCURRED(524, "a timeout occurred");

  private int code;
  private String desc;

  ResponseStatusCode(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  /**
   * gets the http status code
   *
   * @return the status code number
   */
  public int getcode() {
    return code;
  }

  /**
   * get the description
   *
   * @return the description of the status code
   */
  public String getdesc() {
    return desc;
  }
}
