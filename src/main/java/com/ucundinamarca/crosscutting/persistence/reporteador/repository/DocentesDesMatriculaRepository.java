package com.ucundinamarca.crosscutting.persistence.reporteador.repository;

import com.ucundinamarca.crosscutting.domain.dto.moodle.DocentesMatriculaVo;
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
 * DocentesDesMatriculaRepository.
 * Repository for managing student data in Reporteador database.
 * Provides method to list teacher without enrollment.
 *
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
@Log4j2
public class DocentesDesMatriculaRepository {

  @Autowired
  @Qualifier("reporteadorJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  /**
   * Desmatricula docentes basados en criterios específicos.
   *
   * @param grupId    ID del grupo
   * @param pegeId    ID de la persona
   * @param documento Documento de identidad
   * @param peunId    ID del periodo universitario
   * @param usuario   Usuario
   * @param programa  Programa
   * @param unidad    Unidad
   * @param instancia Instancia
   * @return Lista de DocentesMatriculaVO que coinciden con los criterios.
   * @throws Exception en caso de error
   */
  public List<DocentesMatriculaVo> desmatriculaDocentes(
      String grupId, String pegeId, String documento, String peunId, String usuario,
      String programa, String unidad, String instancia) throws Exception {

    StringBuilder sql = buildBaseQuery();
    addConditions(sql, grupId, pegeId, documento, peunId, usuario, programa, unidad, instancia);

    return jdbcTemplate.query(sql.toString(), new RowMapper<DocentesMatriculaVo>() {
      @Override
      public DocentesMatriculaVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapDocentesMatriculaVo(rs);
      }
    });
  }

  private StringBuilder buildBaseQuery() {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT MAMO.MAMO_ID,  USMO.PEGE_ID,  USMO.USMO_IDMOODLE, GRSE.GRSE_IDMOODLE, \n\t");
    sql.append("GRSE.GRSE_ID, PEGE.PEGE_DOCUMENTOIDENTIDAD, PENG.PENG_PRIMERAPELLIDO,    \n\t");
    sql.append("PENG.PENG_SEGUNDOAPELLIDO,    PENG.PENG_PRIMERNOMBRE,    \n\t");
    sql.append("PENG.PENG_SEGUNDONOMBRE,   PENG.PENG_EMAILINSTITUCIONAL, \n\t");
    sql.append("MAMO.ROMO_ID  FROM CAMPOSAPRENDIZAJEUDEC.MATRICULAMOODLE MAMO  \n\t");
    sql.append("INNER JOIN CAMPOSAPRENDIZAJEUDEC.USUARIOMOODLE USMO ");
    sql.append("ON USMO.USMO_ID=MAMO.USMO_ID INNER JOIN CAMPOSAPRENDIZAJEUDEC.GRUPOSEMILLA GRSE ");
    sql.append("ON GRSE.GRSE_ID=MAMO.GRSE_ID INNER JOIN \"GENERAL\".PERSONAGENERAL PEGE ");
    sql.append("ON PEGE.PEGE_ID=USMO.PEGE_ID INNER JOIN \"GENERAL\".PERSONANATURALGENERAL PENG ");
    sql.append("ON PENG.PEGE_ID=PEGE.PEGE_ID LEFT JOIN \"GENERAL\".USUARIOS USUA ");
    sql.append("ON PEGE.PEGE_ID = USUA.PEGE_ID   \n\t");
    sql.append("LEFT JOIN (SELECT  DISTINCT PEGE.PEGE_ID, MAMO.MAMO_ID,  GRSE.GRSE_ID  \n\t");
    sql.append("    FROM    ACADEMICO.GRUPO GRUP  INNER JOIN ACADEMICO.MATERIA MATE ");
    sql.append("    ON GRUP.MATE_CODIGOMATERIA = MATE.MATE_CODIGOMATERIA    \n\t");
    sql.append("    LEFT JOIN ACADEMICO.PENSUMMATERIAGRUPO PEMG ");
    sql.append("    ON GRUP.GRUP_ID = PEMG.GRUP_ID AND GRUP.MATE_CODIGOMATERIA = ");
    sql.append("    PEMG.MATE_CODIGOMATERIA  LEFT JOIN ACADEMICO.PENSUM PENS ");
    sql.append("    ON PEMG.PENS_ID = PENS.PENS_ID LEFT JOIN ACADEMICO.PROGRAMA PROG ");
    sql.append("    ON PENS.PROG_ID = PROG.PROG_ID LEFT JOIN ACADEMICO.MODALIDAD MODA ");
    sql.append("    ON PROG.MODA_ID = MODA.MODA_ID LEFT JOIN CONTRATACIONUDEC.COORDINACIONES COOR");
    sql.append("    ON  COOR.PROG_ID=PROG.PROG_ID  INNER JOIN ACADEMICO.UNIDAD UNID ");
    sql.append("    ON GRUP.UNID_IDREGIONAL = UNID.UNID_ID            \n\t");
    sql.append("    LEFT JOIN ACADEMICOUDEC.GRUPOSAUXILIAR GRAU ");
    sql.append("    ON GRUP.MATE_CODIGOMATERIA = GRAU.MATE_CODIGOMATERIA   \n\t");
    sql.append("    INNER JOIN ACADEMICO.DOCENTEGRUPO DOGR ");
    sql.append("    ON GRUP.GRUP_ID = DOGR.GRUP_ID   \n\t");
    sql.append(" INNER JOIN ACADEMICO.DOCENTEUNIDAD DOUN ON DOGR.DOUN_ID = DOUN.DOUN_ID \n\t");
    sql.append("    INNER JOIN \"GENERAL\".PERSONAGENERAL PEGE ");
    sql.append("    ON DOUN.PEGE_ID = PEGE.PEGE_ID   \n\t");
    sql.append("    INNER JOIN \"GENERAL\".PERSONANATURALGENERAL PENG ");
    sql.append("    ON DOUN.PEGE_ID = PENG.PEGE_ID   \n\t");
    sql.append("    LEFT JOIN \"GENERAL\".USUARIOS USUA ");
    sql.append("    ON PEGE.PEGE_ID = USUA.PEGE_ID   \n\t");
    sql.append("    INNER JOIN CAMPOSAPRENDIZAJEUDEC.USUARIOMOODLE USMO ");
    sql.append("    ON USMO.PEGE_ID=PEGE.PEGE_ID   \n\t");
    sql.append("    INNER JOIN CAMPOSAPRENDIZAJEUDEC.GRUPOSEMILLA GRSE ");
    sql.append("    ON GRSE.GRUP_ID=GRUP.GRUP_ID   \n\t");
    sql.append("    LEFT JOIN CAMPOSAPRENDIZAJEUDEC.SEMILLAMOODLE SEMO ");
    sql.append("    ON SEMO.SEMO_ID=GRSE.SEMO_ID   \n\t");
    sql.append("    LEFT JOIN CAMPOSAPRENDIZAJEUDEC.MATRICULAMOODLE MAMO ");
    sql.append("    ON MAMO.GRSE_ID=GRSE.GRSE_ID AND USMO.USMO_ID=MAMO.USMO_ID  \n\t");
    sql.append("    WHERE   GRUP.GRUP_ACTIVO = 1   \n\t");
    sql.append("    AND DOGR.DOGR_RESPONSABLE = 'TITULAR'   \n\t");
    return sql;
  }

  private void addConditions(
      StringBuilder sql, String grupId, String pegeId, String documento,
      String peunId, String usuario, String programa, String unidad, String instancia) {
    if (peunId != null) {
      sql.append("AND GRUP.PEUN_ID = '").append(peunId).append("' \n\t");
    }
    if (documento != null) {
      sql.append("AND PEGE.PEGE_DOCUMENTOIDENTIDAD = '").append(documento).append("' \n\t");
    }
    if (usuario != null) {
      sql.append("AND USUA.USUA_USUARIO = '").append(usuario).append("' \n\t");
    }
    if (instancia != null) {
      sql.append("AND USMO.INST_ID = '").append(instancia).append("' \n\t");
    }
    if (pegeId != null) {
      sql.append("AND PEGE.PEGE_ID = '").append(pegeId).append("' \n\t");
    }
    if (grupId != null) {
      sql.append("AND GRUP.GRUP_ID = '").append(grupId).append("' \n\t");
    }
    sql.append(")DOCENTES ON  DOCENTES.MAMO_ID=MAMO.MAMO_ID  \n\t");
    sql.append("WHERE MAMO.ROMO_ID IN (3, 9) \n\t");
    sql.append("AND DOCENTES.MAMO_ID IS NULL \n\t");
    if (peunId != null) {
      sql.append("AND GRSE.PEUN_ID = '").append(peunId).append("' \n\t");
    }
    if (pegeId != null) {
      sql.append("AND PEGE.PEGE_ID = '").append(pegeId).append("' \n\t");
    }
    if (grupId != null) {
      sql.append("AND GRSE.GRUP_ID = '").append(grupId).append("' \n\t");
    }
    if (instancia != null) {
      sql.append("AND USMO.INST_ID = '").append(instancia).append("' \n\t");
    }
    if (documento != null) {
      sql.append("AND PEGE.PEGE_DOCUMENTOIDENTIDAD = '").append(documento).append("' \n\t");
    }
    if (usuario != null) {
      sql.append("AND USUA.USUA_USUARIO = '").append(usuario).append("' \n\t");
    }
  }

  private DocentesMatriculaVo mapDocentesMatriculaVo(ResultSet rs) throws SQLException {
    DocentesMatriculaVo docentesMatriculaVo = new DocentesMatriculaVo();
    docentesMatriculaVo.setPengPrimerapellido(rs.getString("PENG_PRIMERAPELLIDO"));
    docentesMatriculaVo.setPengSegundonombre(rs.getString("PENG_SEGUNDONOMBRE"));
    docentesMatriculaVo.setGrseId(rs.getString("GRSE_ID"));
    docentesMatriculaVo.setGrseIdmoodle(rs.getString("GRSE_IDMOODLE"));
    docentesMatriculaVo.setPengPrimernombre(rs.getString("PENG_PRIMERNOMBRE"));
    docentesMatriculaVo.setPengEmailinstitucional(
        rs.getString("PENG_EMAILINSTITUCIONAL"));
    docentesMatriculaVo.setMamoId(rs.getString("MAMO_ID"));
    docentesMatriculaVo.setUsmoIdmoodle(rs.getString("USMO_IDMOODLE"));
    docentesMatriculaVo.setPegeDocumentoidentidad(
        rs.getString("PEGE_DOCUMENTOIDENTIDAD"));
    docentesMatriculaVo.setPengSegundoapellido(rs.getString("PENG_SEGUNDOAPELLIDO"));
    docentesMatriculaVo.setRomoId(rs.getString("ROMO_ID"));
    docentesMatriculaVo.setPegeId(rs.getString("PEGE_ID"));
    return docentesMatriculaVo;
  }
}
