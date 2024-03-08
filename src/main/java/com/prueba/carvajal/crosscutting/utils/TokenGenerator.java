package com.prueba.carvajal.crosscutting.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.carvajal.crosscutting.domain.constants.ExceptionConstants;
import com.prueba.carvajal.crosscutting.domain.constants.SecurityConstants;
import com.prueba.carvajal.crosscutting.domain.dto.autentication.TokenData;
import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
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
 * TokenGenerator
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 07-03-2024
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenGenerator {

    public static String createTokenMacro(TokenData tokenData) {
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
            .issuer(SecurityConstants.CARVAJAL)
            .subject(SecurityConstants.JWT_TOKEN)
            .claim(
                SecurityConstants.USERNAME,
                tokenData.getUsuario().getNombre() + " " + tokenData.getUsuario().getApellido())
            .claim(SecurityConstants.EMAIL, tokenData.getUsuario().getCorreoElectronico())
            .claim(SecurityConstants.IDUSER, tokenData.getUsuario().getUserId()).issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + tokenData.getExpirationTime()))
                .signWith(key)
                .compact();
    }

    public static String createTokenMacroChangePassword(Usuario userMenu) {
        Usuario userToken = userMenu;
        SecretKey key =
                Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().issuer(SecurityConstants.CARVAJAL).subject(SecurityConstants.JWT_TOKEN)
            .claim(SecurityConstants.USERNAME, userToken.getNombre() + " " + userToken.getApellido())
            .claim(SecurityConstants.EMAIL, userToken.getCorreoElectronico())
            .claim(SecurityConstants.IDUSER, userToken.getUserId())
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + 10000))
                .signWith(key)
                .compact();
    }



    public static String getRefreshToken(String token, SecretKey key) throws Exception {
        return Jwts.builder().issuer(SecurityConstants.CARVAJAL).subject(SecurityConstants.JWT_TOKEN)
            .claim(SecurityConstants.USERNAME, String.valueOf(getMapToken(token).get(SecurityConstants.USERNAME)))
            .claim(SecurityConstants.EMAIL, getMapToken(token).get(SecurityConstants.EMAIL))
            .claim(SecurityConstants.IDUSER, getMapToken(token).get(SecurityConstants.IDUSER))
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + SecurityConstants.EXPIRATION))
                .signWith(key)
                .compact();
    }

    public static String getEmailFromToken(String token) throws Exception {
        return String.valueOf(getMapToken(token).get(SecurityConstants.EMAIL));
    }

    public static String getIdUserFromToken(String token) throws Exception {
        return String.valueOf(getMapToken(token).get(SecurityConstants.IDUSER));
    }

    public static String decryptPassword(String password) {
        byte[] decoded = Base64.getDecoder().decode(password);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    public static Map<String, Object> getMapToken(String token) throws  Exception {

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

    public static String getNameFromToken(String token) throws Exception {
        return String.valueOf(getMapToken(token).get(SecurityConstants.USERNAME));
    }


}
