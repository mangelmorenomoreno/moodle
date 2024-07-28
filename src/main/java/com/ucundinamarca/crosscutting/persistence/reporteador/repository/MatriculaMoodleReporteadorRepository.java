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
 * MatriculaMoodleRepository.
 * Repository for managing student data in Reporteador database.
 * Provides method to list students without enrollment.
 *
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
@Log4j2
public class MatriculaMoodleReporteadorRepository {

  @Autowired
  @Qualifier("reporteadorJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  /**
   * Lists students for mass enrollment in Moodle based on various criteria.
   *
   * @param progId     Program ID
   * @param unidId     Unit ID
   * @param peunId     Period ID
   * @param niedId     Educational Level ID
   * @param grupId     Group ID
   * @param pegeId     Person ID
   * @param documento  Document ID
   * @param instId     Instance ID
   * @param codMateria Course Code
   * @param idMoodle   Moodle ID
   * @return List of EstudiantesMatriculaMoodleVo representing students for mass enrollment.
   * @throws Exception if there is an error during the query execution.
   */
  public List<EstudiantesMatriculaMoodleVo> listarEstudiantesMatriculaMasiva(
      String progId, String unidId, String peunId, String niedId, String grupId,
      String pegeId, String documento, String instId, String codMateria, String idMoodle)
      throws Exception {

    String sql = construirConsultaSql();
    sql = agregarCondicionesSql(sql, progId, unidId, peunId, niedId, grupId, pegeId, documento,
        instId, codMateria, idMoodle);

    System.out.println("sql.toString(): " + sql);

    return jdbcTemplate.query(sql, new EstudiantesMatriculaMoodleVoRowMapper());
  }

  private String construirConsultaSql() {
    return "SELECT DISTINCT GRSE.GRUP_ID, GRUP.GRUP_NOMBRE, GRSE.GRSE_IDMOODLE, GRSE.GRSE_ID, "
        + "USMO.USMO_IDMOODLE,USMO.USMO_ID,ESTP.PEGE_ID,ESTP.ESTP_ID,PEGE.PEGE_DOCUMENTOIDENTIDAD,"
        + "PENG.PENG_PRIMERAPELLIDO, PENG.PENG_SEGUNDOAPELLIDO, PENG.PENG_PRIMERNOMBRE, "
        + "PENG.PENG_SEGUNDONOMBRE,PENG.PENG_EMAILINSTITUCIONAL,USUA.USUA_USUARIO,PROG.PROG_NOMBRE,"
        + "PROG.PROG_ID,REPLACE(UNID.UNID_NOMBRE, 'UNIDAD REGIONAL, ') UNID_NOMBRE, UNID.UNID_ID, "
        + "FACU.UNID_NOMBRE FACULTAD, ESTP.ESTP_CODIGOMATRICULA, SITE.SITE_DESCRIPCION, "
        + "CATE.CATE_DESCRIPCION, CASE WHEN USMO.USMO_ID IS NOT NULL THEN 'USUARIO CREADO' "
        + "ELSE 'USUARIO NO CREADO' END ESTADOUSUARIO, CASE WHEN MAMO.MAMO_ID IS NOT NULL "
        + "THEN 'USUARIO MATRICULADO' ELSE 'USUARIO NO MATRICULADO' END ESTADOMATRICULA, "
        + "MAMO.MAMO_ID, INTM.INST_NOMBRE INSTANCIA, TIUM.TIUM_NOMBRE, "
        + "NIED.NIED_DESCRIPCION, NIED.NIED_ID FROM ACADEMICO.ESTUDIANTEPENSUM ESTP "
        + "INNER JOIN ACADEMICO.MATRICULAACADEMICA MAAC ON ESTP.ESTP_ID = MAAC.ESTP_ID "
        + "INNER JOIN ACADEMICO.GRUPOMATRICULADO GRMA ON "
        + "GRMA.MAAC_ID=MAAC.MAAC_ID AND GRMA.GRMA_ESTADO = '1' "
        + "INNER JOIN ACADEMICO.GRUPO GRUP ON GRUP.GRUP_ID=GRMA.GRUP_ID "
        + "INNER JOIN ACADEMICO.MATERIA MATE ON MATE.MATE_CODIGOMATERIA=GRUP.MATE_CODIGOMATERIA "
        + "INNER JOIN CAMPOSAPRENDIZAJEUDEC.GRUPOSEMILLA GRSE ON GRSE.GRUP_ID=GRMA.GRUP_ID "
        + "INNER JOIN CAMPOSAPRENDIZAJEUDEC.SEMILLAMOODLE SEMO ON SEMO.SEMO_ID=GRSE.SEMO_ID "
        + "INNER JOIN \"GENERAL\".PERSONAGENERAL PEGE ON PEGE.PEGE_ID=ESTP.PEGE_ID "
        + "INNER JOIN \"GENERAL\".PERSONANATURALGENERAL PENG ON PENG.PEGE_ID=PEGE.PEGE_ID "
        + "INNER JOIN ACADEMICO.PENSUM PENS ON PENS.PENS_ID=ESTP.PENS_ID "
        + "INNER JOIN ACADEMICO.PROGRAMA PROG ON PROG.PROG_ID=PENS.PROG_ID "
        + "INNER JOIN ACADEMICO.SITUACIONESTUDIANTE SITE ON SITE.SITE_ID=ESTP.SITE_ID "
        + "INNER JOIN ACADEMICO.CATEGORIA CATE ON CATE.CATE_ID=ESTP.CATE_ID "
        + "LEFT JOIN ACADEMICOUDEC.UNIDADPROGRAMA UNPR ON UNPR.PROG_ID=PROG.PROG_ID "
        + "LEFT JOIN ACADEMICO.UNIDAD UNID ON UNID.UNID_ID=ESTP.UNID_ID "
        + "LEFT JOIN \"GENERAL\".USUARIOS USUA ON USUA.PEGE_ID=PEGE.PEGE_ID "
        + "LEFT JOIN ACADEMICO.UNIDAD FACU ON FACU.UNID_ID=UNPR.UNID_ID "
        + "LEFT JOIN CAMPOSAPRENDIZAJEUDEC.USUARIOMOODLE USMO ON USMO.PEGE_ID=PEGE.PEGE_ID "
        + "LEFT JOIN CAMPOSAPRENDIZAJEUDEC.INSTANCIAMOODLE INTM ON INTM.INST_ID=USMO.INST_ID "
        + "LEFT JOIN CAMPOSAPRENDIZAJEUDEC.TIPOUSUARIO TIUM ON TIUM.TIUM_ID=USMO.TIUS_ID "
        + "LEFT JOIN CAMPOSAPRENDIZAJEUDEC.MATRICULAMOODLE MAMO ON MAMO.USMO_ID=USMO.USMO_ID "
        + "AND MAMO.GRSE_ID=GRSE.GRSE_ID "
        + "LEFT JOIN CAMPOSAPRENDIZAJEUDEC.ROLMOODLE ROMO ON ROMO.ROMO_ID=MAMO.ROMO_ID "
        + "LEFT JOIN ACADEMICO.MODALIDAD MODA ON PROG.MODA_ID = MODA.MODA_ID "
        + "LEFT JOIN ACADEMICO.NIVELEDUCATIVO NIED ON MODA.NIED_ID = NIED.NIED_ID "
        + "WHERE MAAC.MAAC_ESTADO = 'ACTIVA' AND MAMO.MAMO_ID IS NULL "
        + "AND USMO.USMO_ID IS NOT NULL ";
  }

  private String agregarCondicionesSql(String sql, String progId, String unidId,
                                       String peunId, String niedId,
                                       String grupId, String pegeId, String documento,
                                       String instId, String codMateria, String idMoodle) {
    StringBuilder sb = new StringBuilder(sql);

    if (codMateria != null) {
      sb.append("AND MATE.MATE_CODIGOMATERIA='").append(codMateria).append("' ");
    }
    if (instId != null) {
      sb.append("AND INTM.INST_ID='").append(instId).append("' ");
    }
    if (documento != null) {
      sb.append("AND PEGE.PEGE_DOCUMENTOIDENTIDAD='").append(documento).append("' ");
    }
    if (progId != null) {
      sb.append("AND PROG.PROG_ID='").append(progId).append("' ");
    }
    if (unidId != null) {
      sb.append("AND UNID.UNID_ID='").append(unidId).append("' ");
    }
    if (niedId != null) {
      if (niedId.equals("1")) {
        sb.append("AND PROG.PROG_NOMBRE NOT LIKE '%SEMESTRE AVANZADO%' ");
      }
      sb.append("AND NIED.NIED_ID='").append(niedId).append("' ");
    }
    if (grupId != null) {
      sb.append("AND GRUP.GRUP_ID='").append(grupId).append("' ");
    }
    if (idMoodle != null) {
      sb.append("AND GRSE.GRSE_IDMOODLE='").append(idMoodle).append("' ");
    }
    if (pegeId != null) {
      sb.append("AND PEGE.PEGE_ID='").append(pegeId).append("' ");
    }
    if (peunId != null) {
      sb.append("AND GRUP.PEUN_ID='").append(peunId).append("' ");
    } else {
      sb.append("AND GRUP.PEUN_ID= (SELECT MAX(PEUN_ID) FROM ACADEMICO.PERIODOUNIVERSIDAD WHERE "
          + "TPPA_ID = 5 AND SYSDATE BETWEEN PEUN_FECHAINICIO AND PEUN_FECHAFIN) ");
    }
    return sb.toString();
  }

  private static class EstudiantesMatriculaMoodleVoRowMapper
      implements RowMapper<EstudiantesMatriculaMoodleVo> {
    @Override
    public EstudiantesMatriculaMoodleVo mapRow(ResultSet rs, int rowNum) throws SQLException {
      EstudiantesMatriculaMoodleVo vo = new EstudiantesMatriculaMoodleVo();
      vo.setNiedId(rs.getString("NIED_ID"));
      vo.setMamoId(rs.getString("MAMO_ID"));
      vo.setPengSegundoApellido(rs.getString("PENG_SEGUNDOAPELLIDO"));
      vo.setEstadoUsuario(rs.getString("ESTADOUSUARIO"));
      vo.setTiumNombre(rs.getString("TIUM_NOMBRE"));
      vo.setEstadoMatricula(rs.getString("ESTADOMATRICULA"));
      vo.setGrseIdMoodle(rs.getString("GRSE_IDMOODLE"));
      vo.setPengEmailInstitucional(rs.getString("PENG_EMAILINSTITUCIONAL"));
      vo.setUsmoIdMoodle(rs.getString("USMO_IDMOODLE"));
      vo.setUsmoId(rs.getString("USMO_ID"));
      vo.setProgNombre(rs.getString("PROG_NOMBRE"));
      vo.setInstancia(rs.getString("INSTANCIA"));
      vo.setUnidNombre(rs.getString("UNID_NOMBRE"));
      vo.setPengSegundoNombre(rs.getString("PENG_SEGUNDONOMBRE"));
      vo.setEstpId(rs.getString("ESTP_ID"));
      vo.setEstpCodigoMatricula(rs.getString("ESTP_CODIGOMATRICULA"));
      vo.setUsuaUsuario(rs.getString("USUA_USUARIO"));
      vo.setNiedDescripcion(rs.getString("NIED_DESCRIPCION"));
      vo.setGrupId(rs.getString("GRUP_ID"));
      vo.setPengPrimerApellido(rs.getString("PENG_PRIMERAPELLIDO"));
      vo.setGrupNombre(rs.getString("GRUP_NOMBRE"));
      vo.setFacultad(rs.getString("FACULTAD"));
      vo.setSiteDescripcion(rs.getString("SITE_DESCRIPCION"));
      vo.setUnidId(rs.getString("UNID_ID"));
      vo.setPengPrimerNombre(rs.getString("PENG_PRIMERNOMBRE"));
      vo.setPegeDocumentoIdentidad(rs.getString("PEGE_DOCUMENTOIDENTIDAD"));
      vo.setProgId(rs.getString("PROG_ID"));
      vo.setCateDescripcion(rs.getString("CATE_DESCRIPCION"));
      vo.setPegeId(rs.getString("PEGE_ID"));
      vo.setGrseId(rs.getString("GRSE_ID"));
      return vo;
    }
  }


}
