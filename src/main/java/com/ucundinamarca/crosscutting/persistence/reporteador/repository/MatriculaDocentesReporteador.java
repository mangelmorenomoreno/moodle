package com.ucundinamarca.crosscutting.persistence.reporteador.repository;

import com.ucundinamarca.crosscutting.domain.dto.moodle.DocentesMatriculaVo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * MatriculaDocentesReporteador.
 * Repository for managing student data in Reporteador database.
 * Provides method to list students without enrollment.
 *
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
public class MatriculaDocentesReporteador {


  @Autowired
  @Qualifier("reporteadorJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  /**
   * Lists teachers for mass enrollment based on various criteria.
   *
   * @param grupId    Group ID
   * @param pegeId    Person ID
   * @param documento Document ID
   * @param peunId    Period ID
   * @param usuario   User ID
   * @param programa  Program ID
   * @param unidad    Unit ID
   * @param instancia Instance ID
   * @return List of DocentesMatriculaVo representing teachers for mass.
   * @throws Exception if there is an error during the query execution.
   */
  public List<DocentesMatriculaVo> listarMatriculaDocenteMasiva(
      String grupId, String pegeId, String documento, String peunId,
      String usuario, String programa, String unidad, String instancia)
      throws Exception {

    String sql = construirconsultaSql(grupId, pegeId, documento, peunId,
        usuario, programa, unidad, instancia);

    return jdbcTemplate.query(sql, new RowMapper<DocentesMatriculaVo>() {
      @Override
      public DocentesMatriculaVo mapRow(ResultSet rs, int rowNum)
          throws SQLException {
        DocentesMatriculaVo vo = new DocentesMatriculaVo();
        vo.setPegeId(rs.getString("PEGE_ID"));
        vo.setPegeDocumentoidentidad(rs.getString(
            "PEGE_DOCUMENTOIDENTIDAD"));
        vo.setPengPrimerapellido(rs.getString("PENG_PRIMERAPELLIDO"));
        vo.setPengSegundoapellido(rs.getString("PENG_SEGUNDOAPELLIDO"));
        vo.setPengPrimernombre(rs.getString("PENG_PRIMERNOMBRE"));
        vo.setPengSegundonombre(rs.getString("PENG_SEGUNDONOMBRE"));
        vo.setPengEmailinstitucional(rs.getString(
            "PENG_EMAILINSTITUCIONAL"));
        vo.setUsuaUsuario(rs.getString("USUA_USUARIO"));
        vo.setUsmoIdmoodle(rs.getString("USMO_IDMOODLE"));
        vo.setPeunId(rs.getString("PEUN_ID"));
        vo.setGrupId(rs.getString("GRUP_ID"));
        vo.setGrupNombre(rs.getString("GRUP_NOMBRE"));
        vo.setGrseIdmoodle(rs.getString("GRSE_IDMOODLE"));
        vo.setRomoId(rs.getString("ROL"));
        vo.setMamoId(rs.getString("MAMO_ID"));
        vo.setUsmoId(rs.getString("USMO_ID"));
        vo.setMateCodigomateria(rs.getString("MATE_CODIGOMATERIA"));
        vo.setGrseId(rs.getString("GRSE_ID"));
        return vo;
      }
    });
  }

  private String construirconsultaSql(
      String grupId, String pegeId, String documento, String peunId,
      String usuario, String programa, String unidad, String instancia) {
    StringBuilder sql = new StringBuilder();
    construirSelect(sql);
    construirFrom(sql);
    construirWhere(sql, grupId, pegeId, documento, peunId, usuario, programa, unidad, instancia);
    return sql.toString();
  }

  private void construirSelect(StringBuilder sql) {
    sql.append("SELECT DISTINCT CASE ")
        .append("WHEN MATE.MATE_CODIGOMATERIA LIKE 'CAD%' THEN '9' ")
        .append("WHEN MATE.MATE_CODIGOMATERIA LIKE 'CAI%' OR MATE.MATE_CODIGOMATERIA ")
        .append(" LIKE 'CTI%' THEN '9' ")
        .append("WHEN MATE.MATE_CODIGOMATERIA LIKE 'DN-%' THEN '9' ")
        .append("ELSE '3' END ROL, ")
        .append("PEGE.PEGE_ID, PEGE.PEGE_DOCUMENTOIDENTIDAD, ")
        .append("PENG.PENG_PRIMERAPELLIDO, PENG.PENG_SEGUNDOAPELLIDO, ")
        .append("PENG.PENG_PRIMERNOMBRE, PENG.PENG_SEGUNDONOMBRE, ")
        .append("PENG.PENG_EMAILINSTITUCIONAL, USUA.USUA_USUARIO, ")
        .append("USMO.USMO_IDMOODLE, GRUP.PEUN_ID, GRUP.GRUP_ID, ")
        .append("GRUP.GRUP_NOMBRE, GRSE.GRSE_IDMOODLE, GRSE.GRSE_ID, ")
        .append("USMO.USMO_IDMOODLE, USMO.USMO_ID, MAMO.MAMO_ID, ")
        .append("MATE.MATE_CODIGOMATERIA ");
  }

  private void construirFrom(StringBuilder sql) {
    sql.append("FROM ACADEMICO.GRUPO GRUP ")
        .append("LEFT JOIN ACADEMICO.UNIDAD UNID ON GRUP.UNID_IDREGIONAL = UNID.UNID_ID ")
        .append("LEFT JOIN ACADEMICO.MATERIA MATE ON")
        .append("GRUP.MATE_CODIGOMATERIA = MATE.MATE_CODIGOMATERIA ")
        .append("LEFT JOIN ACADEMICO.PENSUMMATERIA PEMA ON ")
        .append("GRUP.MATE_CODIGOMATERIA = PEMA.MATE_CODIGOMATERIA AND PEMA.PEMA_ESTADO = 1 ")
        .append("LEFT JOIN ACADEMICO.PENSUM PENS ON PEMA.PENS_ID = PENS.PENS_ID ")
        .append("LEFT JOIN ACADEMICO.PROGRAMA PROG ON PENS.PROG_ID = PROG.PROG_ID ")
        .append("LEFT JOIN ACADEMICO.MODALIDAD MODA ON PROG.MODA_ID = MODA.MODA_ID ")
        .append("LEFT JOIN ACADEMICO.NIVELEDUCATIVO NIED ON MODA.NIED_ID = NIED.NIED_ID ")
        .append("LEFT JOIN CONTRATACIONUDEC.COORDINACIONES COOR ON COOR.PROG_ID=PROG.PROG_ID ")
        .append("INNER JOIN ACADEMICO.DOCENTEGRUPO DOGR ON GRUP.GRUP_ID = DOGR.GRUP_ID ")
        .append("INNER JOIN ACADEMICO.DOCENTEUNIDAD DOUN ON DOGR.DOUN_ID = DOUN.DOUN_ID ")
        .append("INNER JOIN \"GENERAL\".PERSONAGENERAL PEGE ON DOUN.PEGE_ID = PEGE.PEGE_ID ")
        .append("INNER JOIN \"GENERAL\".PERSONANATURALGENERAL PENG ON DOUN.PEGE_ID = PENG.PEGE_ID ")
        .append("LEFT JOIN \"GENERAL\".USUARIOS USUA ON PEGE.PEGE_ID = USUA.PEGE_ID ")
        .append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.USUARIOMOODLE USMO ON USMO.PEGE_ID=PEGE.PEGE_ID ")
        .append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.GRUPOSEMILLA GRSE ON GRSE.GRUP_ID=GRUP.GRUP_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.MATRICULAMOODLE MAMO ON ")
        .append("MAMO.GRSE_ID=GRSE.GRSE_ID AND USMO.USMO_ID=MAMO.USMO_ID ");
  }

  private void construirWhere(
      StringBuilder sql, String grupId, String pegeId, String documento, String peunId,
      String usuario, String programa, String unidad, String instancia) {
    sql.append("WHERE GRUP.GRUP_ACTIVO = 1 AND MAMO.MAMO_ID IS NULL ")
        .append("AND DOGR.DOGR_RESPONSABLE = 'TITULAR' ");

    if (peunId != null) {
      sql.append("AND GRUP.PEUN_ID = '").append(peunId).append("' ");
    } else {
      sql.append("AND GRUP.PEUN_ID= (SELECT MAX(PEUN_ID) FROM ACADEMICO.PERIODOUNIVERSIDAD")
          .append(" WHERE TPPA_ID = 5 AND SYSDATE BETWEEN PEUN_FECHAINICIO AND PEUN_FECHAFIN) ");
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
      sql.append("AND NIED.NIED_ID = '").append(instancia).append("' ");
    }
    if (pegeId != null) {
      sql.append("AND PEGE.PEGE_ID = '").append(pegeId).append("' ");
    }
    if (grupId != null) {
      sql.append("AND GRUP.GRUP_ID = '").append(grupId).append("' ");
    }
  }


}
