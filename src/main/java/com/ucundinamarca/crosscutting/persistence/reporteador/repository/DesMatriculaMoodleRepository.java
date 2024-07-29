package com.ucundinamarca.crosscutting.persistence.reporteador.repository;

import com.ucundinamarca.crosscutting.domain.dto.moodle.EstudiantesMatriculaMoodleVo;
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
 * v.
 * Repository for managing student data in Reporteador database.
 * Provides method to list students without enrollment.
 *
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
@Log4j2
public class DesMatriculaMoodleRepository {

  @Autowired
  @Qualifier("reporteadorJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  /**
   * Lists students for unenrollment based on given criteria.
   *
   * @param instId    Instance ID
   * @param pegeId    Person ID
   * @param grupId    Group ID
   * @param peunId    Period ID
   * @param documento Document ID
   * @return List of EstudiantesMatriculaMoodleVO representing students for unenrollment.
   * @throws Exception if there is an error during the query execution.
   */
  public List<EstudiantesMatriculaMoodleVo> listarDesmatricula(
      String instId, String pegeId, String grupId, String peunId, String documento
  ) throws Exception {
    String sql = construirConsultaSql(grupId, pegeId, instId, peunId, documento);
    return jdbcTemplate.query(sql, new EstudiantesMatriculaMoodleVoRowMapper());
  }

  private String construirConsultaSql(
      String grupId, String pegeId, String instId, String peunId, String documento) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT MAMO.USMO_ID, PEGE.PEGE_DOCUMENTOIDENTIDAD, "
            + "MAMO.GRSE_ID, MAMO.ROMO_ID, MAMO.MAMO_ID, ")
        .append("GRSE.GRUP_ID, GRSE.INST_ID, GRSE.GRSE_IDMOODLE, "
            + "GRSE.GRSE_NOMBRECORTO, GRSE.GRSE_NOMBRELARGO, ")
        .append("USMO.USMO_IDMOODLE, USMO.USMO_USUARIO, ROMO.ROMO_NOMBRE, ROMO.ROMO_DESCRIPCION, ")
        .append("PENG.PENG_PRIMERAPELLIDO, PENG.PENG_SEGUNDOAPELLIDO, PENG.PENG_PRIMERNOMBRE, ")
        .append("PENG.PENG_SEGUNDONOMBRE, PENG.PEGE_ID, GRSE.PEUN_ID ")
        .append("FROM CAMPOSAPRENDIZAJEUDEC.MATRICULAMOODLE MAMO ")
        .append("LEFT JOIN (")
        .append(subconsultaEstudiantesMatriculados(grupId, pegeId, instId, peunId, documento))
        .append(") MATR ON MATR.MAMO_ID = MAMO.MAMO_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.USUARIOMOODLE USMO "
            + "ON MAMO.USMO_ID = USMO.USMO_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.INSTANCIAMOODLE ISMO "
            + "ON ISMO.INST_ID=USMO.INST_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.GRUPOSEMILLA GRSE ON MAMO.GRSE_ID = GRSE.GRSE_ID ")
        .append("INNER JOIN ACADEMICO.GRUPO GRUP ON GRUP.GRUP_ID=GRSE.GRUP_ID ")
        .append("INNER JOIN \"GENERAL\".PERSONAGENERAL PEGE ON PEGE.PEGE_ID=USMO.PEGE_ID ")
        .append("INNER JOIN \"GENERAL\".PERSONANATURALGENERAL PENG ON PEGE.PEGE_ID=PENG.PEGE_ID ")
        .append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.ROLMOODLE ROMO ON ROMO.ROMO_ID=MAMO.ROMO_ID ")
        .append("WHERE MATR.MAMO_ID IS NULL "
            + "AND GRSE.GRSE_IDMOODLE NOT IN (1477) AND ROMO.ROMO_ID=5 ")
        .append(getConditionalSql(grupId, pegeId, instId, peunId, documento))
        .append("ORDER BY PEGE.PEGE_DOCUMENTOIDENTIDAD");

    return sql.toString();
  }

  private String subconsultaEstudiantesMatriculados(
      String grupId, String pegeId, String instId, String peunId, String documento) {
    StringBuilder subSql = new StringBuilder();
    subSql.append("SELECT DISTINCT GRSE.GRUP_ID,GRUP.GRUP_NOMBRE,GRSE.GRSE_IDMOODLE, GRSE.GRSE_ID,")
        .append("USMO.USMO_IDMOODLE, USMO.USMO_ID, ESTP.PEGE_ID, "
            + "ESTP.ESTP_ID, PEGE.PEGE_DOCUMENTOIDENTIDAD, ")
        .append("PENG.PENG_PRIMERAPELLIDO, PENG.PENG_SEGUNDOAPELLIDO, PENG.PENG_PRIMERNOMBRE, ")
        .append("PENG.PENG_SEGUNDONOMBRE, PENG.PENG_EMAILINSTITUCIONAL, USUA.USUA_USUARIO, ")
        .append("PROG.PROG_NOMBRE, PROG.PROG_ID, "
            + "REPLACE(UNID.UNID_NOMBRE, 'UNIDAD REGIONAL, ') UNID_NOMBRE, ")
        .append("UNID.UNID_ID, FACU.UNID_NOMBRE FACULTAD, "
            + "ESTP.ESTP_CODIGOMATRICULA, SITE.SITE_DESCRIPCION, ")
        .append("CATE.CATE_DESCRIPCION, CASE WHEN USMO.USMO_ID IS NOT NULL THEN 'USUARIO CREADO' ")
        .append("ELSE 'USUARIO NO CREADO' END ESTADOUSUARIO, ")
        .append("CASE WHEN MAMO.MAMO_ID IS NOT NULL THEN 'USUARIO MATRICULADO' ")
        .append("ELSE 'USUARIO NO MATRICULADO' END ESTADOMATRICULA, "
            + "MAMO.MAMO_ID, INTM.INST_NOMBRE INSTANCIA, ")
        .append("TIUM.TIUM_NOMBRE, NIED.NIED_DESCRIPCION, NIED.NIED_ID ")
        .append("FROM ACADEMICO.ESTUDIANTEPENSUM ESTP ")
        .append("INNER JOIN ACADEMICO.MATRICULAACADEMICA MAAC ON ESTP.ESTP_ID = MAAC.ESTP_ID ")
        .append("INNER JOIN ACADEMICO.GRUPOMATRICULADO GRMA ON "
            + "GRMA.MAAC_ID = MAAC.MAAC_ID AND GRMA.GRMA_ESTADO = '1' ")
        .append("INNER JOIN ACADEMICO.GRUPO GRUP ON GRUP.GRUP_ID = GRMA.GRUP_ID ")
        .append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.GRUPOSEMILLA GRSE "
            + "ON GRSE.GRUP_ID = GRMA.GRUP_ID ")
        .append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.SEMILLAMOODLE SEMO ON "
            + "SEMO.SEMO_ID = GRSE.SEMO_ID ")
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
        .append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.USUARIOMOODLE USMO "
            + "ON USMO.PEGE_ID = PEGE.PEGE_ID ")
        .append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.INSTANCIAMOODLE INTM "
            + "ON INTM.INST_ID = USMO.INST_ID ")
        .append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.TIPOUSUARIO TIUM ON TIUM.TIUM_ID = USMO.TIUS_ID ")
        .append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.MATRICULAMOODLE MAMO "
            + "ON MAMO.USMO_ID = USMO.USMO_ID AND MAMO.GRSE_ID = GRSE.GRSE_ID ")
        .append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.ROLMOODLE ROMO ON ROMO.ROMO_ID = MAMO.ROMO_ID ")
        .append("INNER JOIN ACADEMICO.MODALIDAD MODA ON PROG.MODA_ID = MODA.MODA_ID ")
        .append("INNER JOIN ACADEMICO.NIVELEDUCATIVO NIED ON MODA.NIED_ID = NIED.NIED_ID ")
        .append("WHERE MAAC.MAAC_ESTADO = 'ACTIVA' ")
        .append(getConditionalSql(grupId, pegeId, instId, peunId, documento));
    return subSql.toString();
  }

  private String getConditionalSql(
      String grupId, String pegeId, String instId, String peunId, String documento) {
    StringBuilder conditions = new StringBuilder();
    if (grupId != null) {
      conditions.append(" AND GRSE.GRUP_ID='").append(grupId).append("'");
    }

    if (grupId != null) {
      conditions.append(" AND GRSE.GRUP_ID='").append(grupId).append("'");
    }
    if (pegeId != null) {
      conditions.append(" AND PEGE.PEGE_ID='").append(pegeId).append("'");
    }
    if (instId != null) {
      conditions.append(" AND INTM.INST_ID='").append(instId).append("'");
    } else {
      conditions.append(" AND INTM.INST_ID='1'");
    }
    if (peunId != null) {
      conditions.append(" AND GRUP.PEUN_ID='").append(peunId).append("'");
    } else {
      conditions.append("AND GRUP.PEUN_ID= (SELECT MAX(PEUN_ID) FROM "
          + "ACADEMICO.PERIODOUNIVERSIDAD WHERE "
          + "TPPA_ID = 5 AND SYSDATE BETWEEN PEUN_FECHAINICIO AND PEUN_FECHAFIN) ");
    }
    if (documento != null) {
      conditions.append(" AND PEGE.PEGE_DOCUMENTOIDENTIDAD='").append(documento).append("'");
    }
    return conditions.toString();
  }

  private static class EstudiantesMatriculaMoodleVoRowMapper
      implements RowMapper<EstudiantesMatriculaMoodleVo> {
    @Override
    public EstudiantesMatriculaMoodleVo mapRow(ResultSet rs, int rowNum) throws SQLException {
      EstudiantesMatriculaMoodleVo vo = new EstudiantesMatriculaMoodleVo();
      vo.setPengSegundoNombre(rs.getString("PENG_SEGUNDONOMBRE"));
      vo.setGrseNombreCorto(rs.getString("GRSE_NOMBRECORTO"));
      vo.setGrseId(rs.getString("GRSE_ID"));
      vo.setUsmoUsuario(rs.getString("USMO_USUARIO"));
      vo.setPengSegundoApellido(rs.getString("PENG_SEGUNDOAPELLIDO"));
      vo.setRomoId(rs.getString("ROMO_ID"));
      vo.setUsmoId(rs.getString("USMO_ID"));
      vo.setRomoDescripcion(rs.getString("ROMO_DESCRIPCION"));
      vo.setRomoNombre(rs.getString("ROMO_NOMBRE"));
      vo.setPengPrimerApellido(rs.getString("PENG_PRIMERAPELLIDO"));
      vo.setGrseNombreLargo(rs.getString("GRSE_NOMBRELARGO"));
      vo.setGrseIdMoodle(rs.getString("GRSE_IDMOODLE"));
      vo.setPengPrimerNombre(rs.getString("PENG_PRIMERNOMBRE"));
      vo.setUsmoIdMoodle(rs.getString("USMO_IDMOODLE"));
      vo.setPeunId(rs.getString("PEUN_ID"));
      vo.setMamoId(rs.getString("MAMO_ID"));
      vo.setGrupId(rs.getString("GRUP_ID"));
      vo.setPegeId(rs.getString("PEGE_ID"));
      vo.setInstId(rs.getString("INST_ID"));
      return vo;
    }
  }


}
