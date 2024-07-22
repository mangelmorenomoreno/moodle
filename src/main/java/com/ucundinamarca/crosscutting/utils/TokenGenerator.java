package com.ucundinamarca.crosscutting.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucundinamarca.crosscutting.domain.constants.ExceptionConstants;
import com.ucundinamarca.crosscutting.domain.constants.SecurityConstants;
import com.ucundinamarca.crosscutting.domain.dto.autentication.TokenData;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.UsuarioMoodle;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * TokenGenerator.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 07-03-2024
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenGenerator {
  /**
   * Crea un token JWT (JSON Web Token) para autenticación basado en los datos proporcionados.
   * Este token incluye varias afirmaciones (claims) como el nombre de usuario, correo electrónico y
   * el identificador del usuario. La clave para firmar el token se genera a partir de una constante
   * de seguridad. El token tiene una fecha de emisión y una fecha de expiración basada en el tiempo
   * de expiración proporcionado.
   *
   * @param tokenData Los datos del token que incluyen usuario y tiempo de expiración.
   * @return Un string que representa el token JWT firmado.
   */
  public static String createTokenMacro(TokenData tokenData) {
    SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY
        .getBytes(StandardCharsets.UTF_8));
    return Jwts.builder().issuer(SecurityConstants.CARVAJAL)
        .subject(SecurityConstants.JWT_TOKEN)
        .claim(SecurityConstants.USERNAME,
            tokenData.getUsuario().getUsmoUsuario())
        .claim(SecurityConstants.IDUSER, tokenData.getUsuario().getPegeId())
        .issuedAt(new Date())
        .expiration(new Date((new Date()).getTime() + tokenData.getExpirationTime()))
        .signWith(key).compact();
  }

  /**
   * Genera un token JWT (JSON Web Token) específicamente para el proceso de cambio de contraseña.
   * Este método crea un token con una duración breve, incorporando afirmaciones (claims) como
   * el nombre y apellido del usuario, su correo electrónico y su identificador único.
   * La clave para la firma del token se deriva de una constante de seguridad y el token está
   * diseñado para tener una corta duración (10 segundos) para mayor seguridad en el proceso de
   * cambio de contraseña.
   *
   * @param userMenu Objeto de tipo Usuario que contiene información del usuario para el cual se
   *                 está creando el token.
   * @return Un string que representa el token JWT firmado y con tiempo limitado de validez.
   */
  public static String createTokenMacroChangePassword(UsuarioMoodle userMenu) {
    SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
    return Jwts.builder().issuer(SecurityConstants.CARVAJAL).subject(SecurityConstants.JWT_TOKEN)
        .claim(SecurityConstants.USERNAME,
            userMenu.getUsmoUsuario())
        .claim(SecurityConstants.IDUSER, userMenu.getPegeId())
        .issuedAt(new Date())
        .issuedAt(new Date()).expiration(new Date((new Date()).getTime() + 10000))
        .signWith(key).compact();
  }


  public static String getIdUserFromToken(String token) throws Exception {
    return String.valueOf(getMapToken(token).get(SecurityConstants.IDUSER));
  }

  public static String decryptPassword(String password) {
    byte[] decoded = Base64.getDecoder().decode(password);
    return new String(decoded, StandardCharsets.UTF_8);
  }

  /**
   * Descompone un token JWT (JSON Web Token) y extrae su contenido en forma de un mapa.
   * El token se divide en partes y la carga útil (payload) es decodificada del formato Base64
   * y convertida en un mapa de objetos para facilitar su acceso y manipulación. Si el token
   * no puede ser procesado correctamente, se lanza una excepción específica relacionada con
   * la decodificación del token.
   *
   * @param token El token JWT como un string que se desea descomponer y analizar.
   * @return Un Mapa con clave de tipo String y valor de tipo Object que contiene los datos
   * @throws Exception Si ocurre un error en la decodificación del token, se lanza una excepción
   *                   con información detallada del error.
   */

  public static Map<String, Object> getMapToken(String token) throws Exception {

    HashMap mapping;
    try {
      String[] chunks = token.split("\\.");
      Base64.Decoder decoder = Base64.getUrlDecoder();
      String payload = new String(decoder.decode(chunks[1]), StandardCharsets.UTF_8);
      mapping = new ObjectMapper().readValue(payload, HashMap.class);
    } catch (Exception e) {
      throw new Exception(ExceptionConstants.DECODE_TOKEN_ERROR, e);
    }
    return mapping;
  }


}
