package com.ucundinamarca.crosscutting.utils;

import java.io.UnsupportedEncodingException;
import org.springframework.stereotype.Component;

/**
 * Utilidad para manejar caracteres especiales en cadenas de texto.
 */
@Component
public class CaracteresEspeciales {

  /**
   * Elimina caracteres especiales de una cadena y los reemplaza por sus equivalentes ASCII.
   *
   * @param str La cadena original que contiene caracteres especiales.
   * @return La cadena sin caracteres especiales.
   * @throws UnsupportedEncodingException Si ocurre un error de codificación.
   */
  public String quitarEspeciales(String str) throws UnsupportedEncodingException {
    if (str == null) {
      return "";
    }

    str = replaceHtmlEntities(str);
    str = decodeUtf8Characters(str);
    str = clearString(str);

    return str;
  }

  /**
   * Reemplaza las entidades HTML con sus equivalentes ASCII.
   *
   * @param str La cadena original que contiene entidades HTML.
   * @return La cadena con entidades HTML reemplazadas.
   */
  private String replaceHtmlEntities(String str) {
    String[][] htmlEntities = {
        {"&aacute;", "a"}, {"&eacute;", "e"}, {"&iacute;", "i"}, {"&oacute;", "o"},
        {"&uacute;", "u"},
        {"&Aacute;", "A"}, {"&Eacute;", "E"}, {"&Iacute;", "I"}, {"&Oacute;", "O"},
        {"&Uacute;", "U"},
        {"&auml;", "a"}, {"&euml;", "e"}, {"&iuml;", "i"}, {"&ouml;", "o"}, {"&uuml;", "u"},
        {"&Auml;", "A"}, {"&Euml;", "E"}, {"&Iuml;", "I"}, {"&Ouml;", "O"}, {"&Uuml;", "U"},
        {"&Ntilde;", "N"}, {"&ntilde;", "n"}, {"&deg;", "_"}, {"&iquest;", "_"}, {"&iexcl;", "_"},
        {"&acirc;", "a"}, {"&ecirc;", "e"}, {"&icirc;", "i"}, {"&ocirc;", "o"}, {"&ucirc;", "u"},
        {"&Acirc;", "A"}, {"&Ecirc;", "E"}, {"&Icirc;", "I"}, {"&Ocirc;", "O"}, {"&Ucirc;", "U"},
        {"&agrave;", "a"}, {"&egrave;", "e"}, {"&igrave;", "i"}, {"&ograve;", "o"},
        {"&ugrave;", "u"},
        {"&Agrave;", "A"}, {"&Egrave;", "E"}, {"&Igrave;", "I"}, {"&Ograve;", "O"},
        {"&Ugrave;", "U"},
        {"&ccedil;", "c"}, {"&Ccedil;", "C"}
    };

    for (String[] entity : htmlEntities) {
      str = str.replace(entity[0], entity[1]);
    }

    return str;
  }

  /**
   * Decodifica los caracteres UTF-8 que están codificados incorrectamente.
   *
   * @param str La cadena original que contiene caracteres UTF-8 codificados incorrectamente.
   * @return La cadena con caracteres UTF-8 decodificados correctamente.
   */
  private String decodeUtf8Characters(String str) {
    String[][] utf8Characters = {
        {"Ã¡", "á"}, {"Ã©", "é"}, {"Ã*", "í"}, {"Ã³", "ó"}, {"Ãº", "ú"},
        {"Ã", "Á"}, {"Ã‰", "É"}, {"Ã", "Í"}, {"Ã“", "Ó"}, {"Ãš", "Ú"},
        {"Ã±", "ñ"}, {"Ã‘", "Ñ"}, {"í“", "Ó"}, {"Ã", "&iacute;"}
    };

    for (String[] utf8Char : utf8Characters) {
      str = str.replace(utf8Char[0], utf8Char[1]);
    }

    return str;
  }

  /**
   * Utility method to replace accented and special characters in a string with their ASCII.
   *
   * @param input the input string containing accented and special characters
   * @return a new string with accented and special characters replaced by their ASCII equivalents
   */
  public static String clearString(String input) {
    // Cadena de caracteres original a sustituir.
    String original = "áàäéèëíìïóòöúùüñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇñÑ";
    // Cadena de caracteres ASCII que reemplazarán los originales.
    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcCnN";
    String output = input;
    for (int i = 0; i < original.length(); i++) {
      output = output.replace(original.charAt(i), ascii.charAt(i));
    }
    return output;
  }

}
