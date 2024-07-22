package com.ucundinamarca.crosscutting.persistence.reporteador.repository;

import com.ucundinamarca.crosscutting.domain.dto.moodle.EstudiantesVo;
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
 * EstudiantesRepository.
 * Repository for managing student data in Reporteador database.
 * Provides method to list students without enrollment.
 *
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
@Log4j2
public class EstudiantesRepository {

  @Autowired
  @Qualifier("reporteadorJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  /**
   * Lists students without enrollment.
   *
   * @param niedId                 Educational level ID.
   * @param pegeId                 General person ID.
   * @param peunId                 University period ID.
   * @param pegeDocumentoidentidad General person identity document.
   * @param facultad               Faculty.
   * @param siteId                 Site ID.
   * @param cateId                 Category ID.
   * @param instId                 Instance ID.
   * @param tiumId                 User type ID.
   * @param progId                 Program ID.
   * @param unidId                 Unit ID.
   * @return List of EstudiantesVo objects representing students without enrollment.
   */
  public List<EstudiantesVo> listarEstudianteSin(String niedId, String pegeId, String peunId,
                                                 String pegeDocumentoidentidad, String facultad,
                                                 String siteId, String cateId, String instId,
                                                 String tiumId, String progId, String unidId) {
    String sql = buildSqlQuery(niedId, pegeId, peunId, pegeDocumentoidentidad, facultad, siteId,
        cateId, instId, tiumId, progId, unidId);
    log.debug("SQL Estudiantes sin matricular: {}", sql);

    return jdbcTemplate.query(sql, new EstudiantesRowMapper());
  }

  private String buildSqlQuery(String niedId, String pegeId, String peunId,
                               String pegeDocumentoidentidad,
                               String facultad, String siteId, String cateId, String instId,
                               String tiumId, String progId, String unidId) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT ESTP.PEGE_ID, ESTP.ESTP_ID, PEGE.PEGE_DOCUMENTOIDENTIDAD, ")
        .append("PENG.PENG_PRIMERAPELLIDO, PENG.PENG_SEGUNDOAPELLIDO, ")
        .append("PENG.PENG_PRIMERNOMBRE, PENG.PENG_SEGUNDONOMBRE, ")
        .append("PENG.PENG_EMAILINSTITUCIONAL, USUA.USUA_USUARIO, ")
        .append("PROG.PROG_NOMBRE, PROG.PROG_ID, ")
        .append("REPLACE(UNID.UNID_NOMBRE, 'UNIDAD REGIONAL, ') UNID_NOMBRE, UNID.UNID_ID, ")
        .append("FACU.UNID_NOMBRE FACULTAD, ESTP.ESTP_CODIGOMATRICULA CODIGO, ")
        .append("SITE.SITE_DESCRIPCION, CATE.CATE_DESCRIPCION, ")
        .append("CASE WHEN USMO.USMO_ID IS NOT NULL THEN 'USUARIO CREADO' ELSE 'USUARIO NO CREADO' "
            + "END ESTADO, ")
        .append("INTM.INST_NOMBRE INSTANCIA, TIUM.TIUM_NOMBRE, "
            + "NIED.NIED_DESCRIPCION, NIED.NIED_ID ")
        .append("FROM ACADEMICO.ESTUDIANTEPENSUM ESTP ")
        .append("INNER JOIN ACADEMICO.MATRICULAACADEMICA MAAC ON ESTP.ESTP_ID = MAAC.ESTP_ID ")
        .append("INNER JOIN \"GENERAL\".PERSONAGENERAL PEGE ON PEGE.PEGE_ID = ESTP.PEGE_ID ")
        .append("INNER JOIN \"GENERAL\".PERSONANATURALGENERAL PENG ON PENG.PEGE_ID = PEGE.PEGE_ID ")
        .append("INNER JOIN ACADEMICO.PENSUM PENS ON PENS.PENS_ID = ESTP.PENS_ID ")
        .append("INNER JOIN ACADEMICO.PROGRAMA PROG ON PROG.PROG_ID = PENS.PROG_ID ")
        .append("INNER JOIN ACADEMICO.SITUACIONESTUDIANTE SITE ON SITE.SITE_ID = ESTP.SITE_ID ")
        .append("INNER JOIN ACADEMICO.CATEGORIA CATE ON CATE.CATE_ID = ESTP.CATE_ID ")
        .append("LEFT JOIN ACADEMICOUDEC.UNIDADPROGRAMA UNPR ON UNPR.PROG_ID = PROG.PROG_ID ")
        .append("LEFT JOIN ACADEMICO.UNIDAD UNID ON UNID.UNID_ID = ESTP.UNID_ID ")
        .append("LEFT JOIN \"GENERAL\".USUARIOS USUA ON USUA.PEGE_ID = PEGE.PEGE_ID ")
        .append("LEFT JOIN ACADEMICO.UNIDAD FACU ON FACU.UNID_ID = UNPR.UNID_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.USUARIOMOODLE USMO "
            + "ON USMO.PEGE_ID = PEGE.PEGE_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.INSTANCIAMOODLE INTM "
            + "ON INTM.INST_ID = USMO.INST_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.TIPOUSUARIO TIUM ON TIUM.TIUM_ID = USMO.TIUS_ID ")
        .append("INNER JOIN ACADEMICO.MODALIDAD MODA ON PROG.MODA_ID = MODA.MODA_ID ")
        .append("INNER JOIN ACADEMICO.NIVELEDUCATIVO NIED ON MODA.NIED_ID = NIED.NIED_ID ")
        .append("WHERE MAAC.MAAC_ESTADO = 'ACTIVA' AND USMO.PEGE_ID IS NULL "
            + "AND PENG.PENG_EMAILINSTITUCIONAL IS NOT NULL AND USUA.USUA_USUARIO IS NOT NULL ");
    appendConditionalFilters(sql, niedId, pegeId, peunId, pegeDocumentoidentidad,
        facultad, siteId, cateId, instId, tiumId, progId, unidId);
    return sql.toString();
  }

  private void appendConditionalFilters(
      StringBuilder sql, String niedId, String pegeId,
      String peunId, String pegeDocumentoidentidad,
      String facultad, String siteId,
      String cateId, String instId, String tiumId, String progId, String unidId) {
    if (niedId != null) {
      sql.append(" AND NIED.NIED_ID = '").append(niedId).append("'");
    }
    if (peunId != null) {
      sql.append(" AND MAAC.PEUN_ID = '").append(peunId).append("'");
    }
    if (pegeDocumentoidentidad != null) {
      sql.append(" AND PEGE.PEGE_DOCUMENTOIDENTIDAD = '")
          .append(pegeDocumentoidentidad).append("'");
    }
    if (pegeId != null) {
      sql.append(" AND PEGE.PEGE_ID = '").append(pegeId).append("'");
    }
    if (facultad != null) {
      sql.append(" AND FACU.UNID_ID = '").append(facultad).append("'");
    }
    if (siteId != null) {
      sql.append(" AND ESTP.SITE_ID = '").append(siteId).append("'");
    }
    if (cateId != null) {
      sql.append(" AND ESTP.CATE_ID = '").append(cateId).append("'");
    }
    if (instId != null) {
      sql.append(" AND INTM.INST_ID = '").append(instId).append("'");
    }
    if (tiumId != null) {
      sql.append(" AND TIUM.TIUM_ID = '").append(tiumId).append("'");
    }
    if (progId != null) {
      sql.append(" AND PROG.PROG_ID = '").append(progId).append("'");
    }
    if (unidId != null) {
      sql.append(" AND UNID.UNID_ID = '").append(unidId).append("'");
    }
  }

  private static class EstudiantesRowMapper implements RowMapper<EstudiantesVo> {
    @Override
    public EstudiantesVo mapRow(ResultSet rs, int rowNum) throws SQLException {
      EstudiantesVo estudiantesVo = new EstudiantesVo();
      estudiantesVo.setPengSegundonombre(rs.getString("PENG_SEGUNDONOMBRE"));
      estudiantesVo.setPengSegundoapellido(rs.getString("PENG_SEGUNDOAPELLIDO"));
      estudiantesVo.setUsuaUsuario(rs.getString("USUA_USUARIO"));
      estudiantesVo.setTiumNombre(rs.getString("TIUM_NOMBRE"));
      estudiantesVo.setPengPrimerapellido(rs.getString("PENG_PRIMERAPELLIDO"));
      estudiantesVo.setEstado(rs.getString("ESTADO"));
      estudiantesVo.setCodigo(rs.getString("CODIGO"));
      estudiantesVo.setFacultad(rs.getString("FACULTAD"));
      estudiantesVo.setSiteDescripcion(rs.getString("SITE_DESCRIPCION"));
      estudiantesVo.setPengPrimernombre(rs.getString("PENG_PRIMERNOMBRE"));
      estudiantesVo.setPengEmailinstitucional(rs.getString("PENG_EMAILINSTITUCIONAL"));
      estudiantesVo.setProgNombre(rs.getString("PROG_NOMBRE"));
      estudiantesVo.setPegeDocumentoidentidad(rs.getString("PEGE_DOCUMENTOIDENTIDAD"));
      estudiantesVo.setCateDescripcion(rs.getString("CATE_DESCRIPCION"));
      estudiantesVo.setInstancia(rs.getString("INSTANCIA"));
      estudiantesVo.setPegeId(rs.getInt("PEGE_ID"));
      estudiantesVo.setUnidNombre(rs.getString("UNID_NOMBRE"));
      estudiantesVo.setEstpId(rs.getInt("ESTP_ID"));
      estudiantesVo.setProgId(rs.getInt("PROG_ID"));
      estudiantesVo.setUnidId(rs.getInt("UNID_ID"));
      estudiantesVo.setNiedId(rs.getInt("NIED_ID"));
      estudiantesVo.setNiedDescripcion(rs.getString("NIED_DESCRIPCION"));
      return estudiantesVo;
    }
  }
}
