package com.prueba.carvajal.crosscutting.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Domain to manage orders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-06
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

  private Long id;
  private String code;
  private Long price;

}
