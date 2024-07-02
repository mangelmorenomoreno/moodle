package com.ucundinamarca.crosscutting.domain.transactions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domain to manage DocumentResponse.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {

  private String type;
  private String number;
}
