package com.prueba.carvajal.crosscutting.domain.dto.autentication;


import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TokenData.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenData implements Serializable {

  private static final long serialVersionUID = -7029122565299646442L;
  Usuario usuario;
  Long expirationTime;
}
