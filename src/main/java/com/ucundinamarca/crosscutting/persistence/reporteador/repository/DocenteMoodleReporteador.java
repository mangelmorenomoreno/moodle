package com.ucundinamarca.crosscutting.persistence.reporteador.repository;

import com.ucundinamarca.crosscutting.domain.dto.moodle.DocentesVo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * DocenteMoodleReporteador.
 * Repository for managing student data in Reporteador database.
 * Provides method to list without enrollment.
 *
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
@Log4j2
public class DocenteMoodleReporteador {

  @Autowired
  @Qualifier("reporteadorJdbcTemplate")
  private JdbcTemplate jdbcTemplate;


  /**
   * Finds all docentes based on specific criteria.
   *
   * @param pegeId    Person ID
   * @param documento Document ID
   * @param peunId    Period ID
   * @param usuario   User
   * @param programa  Program
   * @param unidad    Unit
   * @param instancia Instance
   * @return List of DocentesVo representing the docentes that match the criteria.
   * @throws Exception if there is an error during the query execution.
   */
  public List<DocentesVo> registroListarDocente(
      String pegeId, String documento, String peunId, String usuario,
      String programa, String unidad, String instancia) throws Exception {
    String sql = construirConsultaSql(
        pegeId, documento, peunId, usuario, programa, unidad, instancia);
    return jdbcTemplate.query(sql, new RowMapper<DocentesVo>() {
      @Override
      public DocentesVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapearDocente(rs);
      }
    });
  }

  private String construirConsultaSql(String pegeId, String documento,
                                      String peunId, String usuario,
                                      String programa, String unidad,
                                      String instancia) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT DISTINCT PEGE.PEGE_ID, PEGE.PEGE_DOCUMENTOIDENTIDAD, ")
        .append("PENG.PENG_PRIMERAPELLIDO, PENG.PENG_SEGUNDOAPELLIDO, ")
        .append("PENG.PENG_PRIMERNOMBRE, PENG.PENG_SEGUNDONOMBRE, ")
        .append("PENG.PENG_EMAILINSTITUCIONAL, USUA.USUA_USUARIO, ")
        .append("USMO.USMO_IDMOODLE, GRUP.PEUN_ID ")
        .append("FROM ACADEMICO.GRUPO GRUP ")
        .append("INNER JOIN ACADEMICO.MATERIA MATE ON ")
        .append("GRUP.MATE_CODIGOMATERIA = MATE.MATE_CODIGOMATERIA ")
        .append("LEFT JOIN ACADEMICO.PENSUMMATERIAGRUPO PEMG ON ")
        .append("GRUP.GRUP_ID = PEMG.GRUP_ID AND ")
        .append("GRUP.MATE_CODIGOMATERIA = PEMG.MATE_CODIGOMATERIA ")
        .append("LEFT JOIN ACADEMICO.PENSUM PENS ON PEMG.PENS_ID = PENS.PENS_ID ")
        .append("LEFT JOIN ACADEMICO.PROGRAMA PROG ON PENS.PROG_ID = PROG.PROG_ID ")
        .append("LEFT JOIN ACADEMICO.MODALIDAD MODA ON PROG.MODA_ID = MODA.MODA_ID ")
        .append("LEFT JOIN CONTRATACIONUDEC.COORDINACIONES COOR ON ")
        .append("COOR.PROG_ID = PROG.PROG_ID ")
        .append("INNER JOIN ACADEMICO.UNIDAD UNID ON ")
        .append("GRUP.UNID_IDREGIONAL = UNID.UNID_ID ")
        .append("LEFT JOIN ACADEMICOUDEC.GRUPOSAUXILIAR GRAU ON ")
        .append("GRUP.MATE_CODIGOMATERIA = GRAU.MATE_CODIGOMATERIA ")
        .append("INNER JOIN ACADEMICO.DOCENTEGRUPO DOGR ON ")
        .append("GRUP.GRUP_ID = DOGR.GRUP_ID ")
        .append("INNER JOIN ACADEMICO.DOCENTEUNIDAD DOUN ON ")
        .append("DOGR.DOUN_ID = DOUN.DOUN_ID ")
        .append("INNER JOIN ACADEMICO.UNIDAD UNIDO ON ")
        .append("DOUN.UNID_ID = UNIDO.UNID_ID ")
        .append("INNER JOIN \"GENERAL\".PERSONAGENERAL PEGE ON ")
        .append("DOUN.PEGE_ID = PEGE.PEGE_ID ")
        .append("INNER JOIN \"GENERAL\".PERSONANATURALGENERAL PENG ON ")
        .append("DOUN.PEGE_ID = PENG.PEGE_ID ")
        .append("LEFT JOIN \"GENERAL\".USUARIOS USUA ON ")
        .append("PEGE.PEGE_ID = USUA.PEGE_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.USUARIOMOODLE USMO ON ")
        .append("USMO.PEGE_ID = PEGE.PEGE_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.GRUPOSEMILLA GRSE ON ")
        .append("GRSE.GRUP_ID = GRUP.GRUP_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.SEMILLAMOODLE SEMO ON ")
        .append("SEMO.SEMO_ID = GRSE.SEMO_ID ")
        .append("WHERE GRUP.GRUP_ACTIVO = 1 AND USMO.PEGE_ID IS NULL ")
        .append("AND PENG.PENG_EMAILINSTITUCIONAL IS NOT NULL ")
        .append("AND USUA.USUA_USUARIO IS NOT NULL ");

    agregarCondiciones(sql, pegeId, documento, peunId, usuario, programa, unidad, instancia);
    sql.append("AND DOGR.DOGR_RESPONSABLE = 'TITULAR' ");
    return sql.toString();
  }

  private void agregarCondiciones(StringBuilder sql, String pegeId, String documento,
                                  String peunId, String usuario, String programa,
                                  String unidad, String instancia) {
    if (peunId != null) {
      sql.append("AND GRUP.PEUN_ID = '").append(peunId).append("' ");
    } else {
      sql.append("AND GRUP.PEUN_ID = (SELECT MAX(PEUN_ID) FROM ")
          .append("ACADEMICO.PERIODOUNIVERSIDAD WHERE TPPA_ID = 5 ")
          .append("AND SYSDATE BETWEEN PEUN_FECHAINICIO AND PEUN_FECHAFIN) ");
    }
    if (documento != null) {
      sql.append("AND PEGE.PEGE_DOCUMENTOIDENTIDAD = '").append(documento).append("' ");
    }
    if (usuario != null) {
      sql.append("AND USUA.USUA_USUARIO = '").append(usuario).append("' ");
    }
    if (programa != null) {
      sql.append("AND PROG.PROG_ID = '").append(programa).append("' ");
    }
    if (unidad != null) {
      sql.append("AND UNID.UNID_ID = '").append(unidad).append("' ");
    }
    if (instancia != null) {
      sql.append("AND USMO.INST_ID = '").append(instancia).append("' ");
    }
    if (pegeId != null) {
      sql.append("AND PEGE.PEGE_ID = '").append(pegeId).append("' ");
    }
  }

  private DocentesVo mapearDocente(ResultSet rs) throws SQLException {
    DocentesVo docente = new DocentesVo();
    docente.setPegeId(rs.getString("PEGE_ID"));
    docente.setPegeDocumentoidentidad(rs.getString("PEGE_DOCUMENTOIDENTIDAD"));
    docente.setPengPrimerapellido(rs.getString("PENG_PRIMERAPELLIDO"));
    docente.setPengSegundoapellido(rs.getString("PENG_SEGUNDOAPELLIDO"));
    docente.setPengPrimernombre(rs.getString("PENG_PRIMERNOMBRE"));
    docente.setPengSegundonombre(rs.getString("PENG_SEGUNDONOMBRE"));
    docente.setPengEmailinstitucional(rs.getString("PENG_EMAILINSTITUCIONAL"));
    docente.setUsuaUsuario(rs.getString("USUA_USUARIO"));
    docente.setUsmoIdmoodle(rs.getString("USMO_IDMOODLE"));
    docente.setPeunId(rs.getString("PEUN_ID"));
    return docente;
  }

}
