package com.ucundinamarca.crosscutting.utils;

import com.ucundinamarca.crosscutting.domain.dto.autentication.ConexionVo;
import org.json.JSONException;
import org.springframework.stereotype.Component;

/**
 * Conexion.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Component
public class Conexion {
  /**
   * Configura la conexión para crear un estudiante de pregrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para crear un estudiante de pregrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionPregradoCrearEstudiante() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("core_user_create_users");
    conexionVo.setWstoken("620423c2694c348f23a7c2f0f615b786");
    conexionVo.setUrl("https://pregrado.ucundinamarca.edu.co/webservice/rest/server.php");
    return conexionVo;
  }

  /**
   * Configura la conexión para crear un estudiante de posgrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para crear un estudiante de posgrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionPosgradoCrearEstudiante() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("core_user_create_users");
    conexionVo.setWstoken("2fac8fd102feceb97c4c448e2cf522b7");
    conexionVo.setUrl("https://posgrados.ucundinamarca.edu.co/webservice/rest/server.php");
    return conexionVo;
  }

  /**
   * Configura la conexión para crear grupos de pregrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para crear grupos de pregrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionPregradoCrearGrupos() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("core_course_duplicate_course");
    conexionVo.setWstoken("620423c2694c348f23a7c2f0f615b786");

    conexionVo.setUrl("https://pregrado.ucundinamarca.edu.co/webservice/rest/server.php");

    return conexionVo;
  }

  /**
   * Configura la conexión para crear grupos de posgrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para crear grupos de posgrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionProsgradoCrearGrupos() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("core_course_duplicate_course");
    conexionVo.setWstoken("2fac8fd102feceb97c4c448e2cf522b7");

    conexionVo.setUrl("https://posgrados.ucundinamarca.edu.co/webservice/rest/server.php");

    return conexionVo;
  }

  /**
   * Configura la conexión para matricular estudiantes de pregrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para matricular estudiantes de pregrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionPregradoMatriculaMoodle() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("enrol_manual_enrol_users");
    conexionVo.setWstoken("620423c2694c348f23a7c2f0f615b786");

    conexionVo.setUrl("https://pregrado.ucundinamarca.edu.co/webservice/rest/server.php");

    return conexionVo;
  }

  /**
   * Configura la conexión para limpiar grupos de pregrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para limpiar grupos de pregrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionPregradoLimpiarGrupos() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("core_enrol_get_enrolled_users");
    conexionVo.setWstoken("d6eeaf63cd033dda551af1c272863852");
    conexionVo.setUrl("https://pregrado.ucundinamarca.edu.co/webservice/rest/server.php");
    return conexionVo;
  }

  /**
   * Configura la conexión para desmatricular estudiantes de pregrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para desmatricular estudiantes de pregrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionPregradoDesMatriculaMoodle() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("enrol_manual_unenrol_users");
    conexionVo.setWstoken("620423c2694c348f23a7c2f0f615b786");

    conexionVo.setUrl("https://pregrado.ucundinamarca.edu.co/webservice/rest/server.php");

    return conexionVo;
  }

  /**
   * Configura la conexión para matricular estudiantes de posgrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para matricular estudiantes de posgrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionPosgradoMatriculaMoodle() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("enrol_manual_enrol_users");
    conexionVo.setWstoken("620423c2694c348f23a7c2f0f615b786");

    conexionVo.setUrl("https://posgrados.ucundinamarca.edu.co/webservice/rest/server.php");

    return conexionVo;
  }

  /**
   * Configura la conexión para desmatricular estudiantes de posgrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para desmatricular estudiantes de posgrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionPosgradoDesMatriculaMoodle() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("enrol_manual_unenrol_users");
    conexionVo.setWstoken("620423c2694c348f23a7c2f0f615b786");

    conexionVo.setUrl("https://posgrados.ucundinamarca.edu.co/webservice/rest/server.php");

    return conexionVo;
  }

  /**
   * Configura la conexión para extraer notas de estudiantes de pregrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para extraer notas de estudiantes de pregrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionPregradoExtraccionNotas() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("cundinamarca_grade_course");
    conexionVo.setWstoken("620423c2694c348f23a7c2f0f615b786");

    conexionVo.setUrl("https://pregrado.ucundinamarca.edu.co/webservice/rest/server.php");

    return conexionVo;
  }

  /**
   * Configura la conexión para extraer notas de estudiantes de posgrado en Moodle.
   *
   * @return Un objeto {@link ConexionVo} configurado para extraer notas de estudiantes de posgrado.
   * @throws JSONException Si ocurre un error al construir el objeto JSON.
   */
  public ConexionVo conexionPosgradoExtraccionNotas() throws JSONException {
    ConexionVo conexionVo = new ConexionVo();
    conexionVo.setMoodlewsrestformat("json");
    conexionVo.setWsfunction("cundinamarca_grade_course");
    conexionVo.setWstoken("620423c2694c348f23a7c2f0f615b786");

    conexionVo.setUrl("https://posgrados.ucundinamarca.edu.co/webservice/rest/server.php");

    return conexionVo;
  }

}
