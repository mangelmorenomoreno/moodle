package com.ucundinamarca.crosscutting.persistence.reporteador.repository;

import com.ucundinamarca.crosscutting.domain.dto.moodle.GruposPlataformaVo;
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
 * GruposPlataformaRepository.
 * Repository for managing student data in Reporteador database.
 * Provides method to list students without enrollment.
 *
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
@Log4j2
public class GruposPlataformaRepository {

  @Autowired
  @Qualifier("reporteadorJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  /**
   * Lists all platform groups based on specific criteria.
   *
   * @param grupId     Group ID
   * @param peunId     Period ID
   * @param unidId     Unit ID
   * @param progId     Program ID
   * @param niedId     Education level ID
   * @param codMateria Subject code
   * @param tipoCadi   Type of CADI
   * @param duplicado  Duplicate flag
   * @return List of GruposPlataformaVo representing the platform groups.
   * @throws Exception in case of errors
   */
  public List<GruposPlataformaVo> listarGruposPlataformamsivo(
      String grupId, String peunId, String unidId, String progId,
      String niedId, String codMateria, String tipoCadi, String duplicado) throws Exception {
    String sql = buildSqlQuery(
        grupId, peunId, unidId, progId, niedId, codMateria, tipoCadi, duplicado);

    log.info("SQL CREACION GRUPOS");
    log.info(sql);
    return jdbcTemplate.query(sql, new RowMapper<GruposPlataformaVo>() {
      @Override
      public GruposPlataformaVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapGruposPlataforma(rs);
      }
    });
  }

  private String buildSqlQuery(
      String grupId, String peunId, String unidId, String progId,
      String niedId, String codMateria, String tipoCadi, String duplicado) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT * FROM ( ")
        .append("SELECT DISTINCT ")
        .append("MATE.MATE_NOMBRE, GRUP.GRUP_ID, ")
        .append("CASE WHEN CAMO_PENS.CAMO_ID IS NOT NULL "
            + "THEN CAMO_PENS.CAMO_IDMOODLE ")
        .append("WHEN CAMO_MATE.CAMO_ID IS NOT NULL "
            + "THEN CAMO_MATE.CAMO_IDMOODLE ELSE 268 END CATE_ID, ")
        .append("CASE WHEN MATE.MATE_CODIGOMATERIA LIKE 'CAD%' "
            + "OR MATE.MATE_CODIGOMATERIA LIKE 'CFC%' ")
        .append("THEN SEMO.SEMO_ID WHEN MATE.MATE_CODIGOMATERIA "
            + "LIKE 'CAI%' OR MATE.MATE_CODIGOMATERIA LIKE 'CTI%' ")
        .append("THEN SEMO.SEMO_ID WHEN MATE.MATE_CODIGOMATERIA "
            + "LIKE 'DN-%' THEN SEMO.SEMO_ID ELSE 397 END SEMO_ID, ")
        .append("CASE WHEN MATE.MATE_CODIGOMATERIA LIKE 'CAD%' "
            + "OR MATE.MATE_CODIGOMATERIA LIKE 'CFC%' ")
        .append("THEN SEMO.SEMO_NOMBRELARGO WHEN MATE.MATE_CODIGOMATERIA "
            + "LIKE 'CAI%' OR MATE.MATE_CODIGOMATERIA LIKE 'CTI%' ")
        .append("THEN SEMO.SEMO_NOMBRELARGO WHEN MATE.MATE_CODIGOMATERIA "
            + "LIKE 'DN-%' THEN SEMO.SEMO_NOMBRELARGO ")
        .append("ELSE 'PLANTILLA_NUCLEOS_TEMATICOS' END SEMO_NOMBRELARGO, ")
        .append("CASE WHEN MATE.MATE_CODIGOMATERIA LIKE 'CAD%' "
            + "OR MATE.MATE_CODIGOMATERIA LIKE 'CFC%' ")
        .append("THEN SEMO.SEMO_NOMBRECORTO WHEN MATE.MATE_CODIGOMATERIA "
            + "LIKE 'CAI%' OR MATE.MATE_CODIGOMATERIA LIKE 'CTI%' ")
        .append("THEN SEMO.SEMO_NOMBRECORTO WHEN MATE.MATE_CODIGOMATERIA "
            + "LIKE 'DN-%' THEN SEMO.SEMO_NOMBRECORTO ")
        .append("ELSE 'PLANTILLA_NUCLEOS_TEMATICOS' END SEMO_NOMBRECORTO, "
            + "SEMO.CADI_ID, SEMO.CAI_ID, ")
        .append("CASE WHEN MATE.MATE_CODIGOMATERIA LIKE 'CAD%' "
            + "OR MATE.MATE_CODIGOMATERIA LIKE 'CFC%' ")
        .append("THEN SEMO.SEMO_IDMOODLE WHEN MATE.MATE_CODIGOMATERIA "
            + "LIKE 'CAI%' OR MATE.MATE_CODIGOMATERIA LIKE 'CTI%' ")
        .append("THEN SEMO.SEMO_IDMOODLE WHEN MATE.MATE_CODIGOMATERIA "
            + "LIKE 'DN-%' THEN SEMO.SEMO_IDMOODLE ELSE '1369' END SEMO_IDMOODLE, ")
        .append("CASE WHEN NIED.NIED_DESCRIPCION IS NOT NULL "
            + "THEN NIED.NIED_DESCRIPCION ELSE 'PREGRADO' END NIED_DESCRIPCION, ")
        .append("CASE WHEN NIED.NIED_ID IS NOT NULL "
            + "THEN NIED.NIED_ID ELSE 1 END NIED_ID, UNID.UNID_ID, ")
        .append("UNID.UNID_NOMBRE UNIDAD, GRUP.PEUN_ID, GRSE.GRSE_IDMOODLE, ")
        .append("CASE WHEN MATE.MATE_CODIGOMATERIA LIKE 'CAD%' "
            + "OR MATE.MATE_CODIGOMATERIA LIKE 'CFC%' THEN 'DISCIPLINAR' ")
        .append("WHEN MATE.MATE_CODIGOMATERIA LIKE 'CAI%' "
            + "OR MATE.MATE_CODIGOMATERIA LIKE 'CTI%' THEN 'INSTITUCIONAL' ")
        .append("WHEN MATE.MATE_CODIGOMATERIA LIKE 'DN-%' "
            + "THEN 'D/N' WHEN MATE.MATE_CODIGOMATERIA LIKE 'COMP-%' THEN 'NUCLEOS' ")
        .append("ELSE 'NUCLEOS' END TIPO_CADI, MATE.MATE_CODIGOMATERIA, GRUP.GRUP_NOMBRE, ")
        .append("GRUP.GRUP_ID || '/' || MATE.MATE_NOMBRE || '/' || ")
        .append("(CASE WHEN GRUP.UNID_IDREGIONAL = '13' "
            + "THEN 'FU' WHEN GRUP.UNID_IDREGIONAL = '14' THEN 'UB' ")
        .append("WHEN GRUP.UNID_IDREGIONAL = '15' "
            + "THEN 'GT' WHEN GRUP.UNID_IDREGIONAL = '16' THEN 'FC' ")
        .append("WHEN GRUP.UNID_IDREGIONAL = '17' THEN 'CH' "
            + "WHEN GRUP.UNID_IDREGIONAL = '19' THEN 'SO' ")
        .append("WHEN GRUP.UNID_IDREGIONAL = '20' THEN 'ZP' END) "
            + "|| '/' || GRUP.MATE_CODIGOMATERIA || '/' || GRUP.GRUP_NOMBRE NOMBRELARGO ")
        .append("FROM ACADEMICO.GRUPO GRUP ")
        .append("INNER JOIN ACADEMICO.UNIDAD UNID ON GRUP.UNID_IDREGIONAL = UNID.UNID_ID ")
        .append("INNER JOIN ACADEMICO.MATERIA MATE ON "
            + "GRUP.MATE_CODIGOMATERIA = MATE.MATE_CODIGOMATERIA ")
        .append("LEFT JOIN ACADEMICO.PENSUMMATERIAGRUPO PEMG "
            + "ON GRUP.GRUP_ID = PEMG.GRUP_ID AND "
            + "MATE.MATE_CODIGOMATERIA = PEMG.MATE_CODIGOMATERIA ")
        .append("LEFT JOIN ACADEMICO.PENSUM PENS ON PEMG.PENS_ID = PENS.PENS_ID ")
        .append("LEFT JOIN ACADEMICO.PROGRAMA PROG ON PENS.PROG_ID = PROG.PROG_ID ")
        .append("LEFT JOIN ACADEMICO.MODALIDAD MODA ON PROG.MODA_ID = MODA.MODA_ID ")
        .append("LEFT JOIN ACADEMICO.NIVELEDUCATIVO NIED ON MODA.NIED_ID = NIED.NIED_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.SEMILLAMOODLE SEMO "
            + "ON MATE.MATE_CODIGOMATERIA = SEMO.MATE_CODIGOMATERIA AND SEMO.SEMI_ESTADO = 1 ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.GRUPOSEMILLA GRSE ON GRSE.GRUP_ID = GRUP.GRUP_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.CATEGORIASMOODLE CAMO_PENS "
            + "ON PENS.PENS_ID = CAMO_PENS.PENS_ID AND "
            + "GRUP.UNID_IDREGIONAL = CAMO_PENS.UNID_ID AND CAMO_PENS.PEUN_ID = GRUP.PEUN_ID ")
        .append("LEFT JOIN CAMPOSAPRENDIZAJEUDEC.CATEGORIASMOODLE CAMO_MATE "
            + "ON GRUP.MATE_CODIGOMATERIA = CAMO_MATE.MATE_CODIGOMATERIA "
            + "AND GRUP.UNID_IDREGIONAL = CAMO_MATE.UNID_ID AND CAMO_MATE.PEUN_ID = GRUP.PEUN_ID ")
        .append("LEFT JOIN (SELECT UP.PROG_ID, UN.UNID_NOMBRE "
            + "FROM ACADEMICOUDEC.UNIDADPROGRAMA UP, ACADEMICO.UNIDAD UN "
            + "WHERE UP.UNID_ID = UN.UNID_ID) UNPR ON PENS.PROG_ID = UNPR.PROG_ID ")
        .append("LEFT JOIN ACADEMICOUDEC.GRUPOSAUXILIAR GRAU "
            + "ON GRUP.MATE_CODIGOMATERIA = GRAU.MATE_CODIGOMATERIA ")
        .append("WHERE GRUP.GRUP_ACTIVO = 1 AND GRUP.MATE_CODIGOMATERIA NOT LIKE '%1299' ");

    appendOptionalConditions(sql, grupId, peunId, unidId, progId, niedId, codMateria, duplicado);

    sql.append("ORDER BY UNIDAD, NOMBRELARGO) DATOS WHERE DATOS.SEMO_ID IS NOT NULL ");

    if (tipoCadi != null) {
      sql.append("AND DATOS.TIPO_CADI LIKE '").append(tipoCadi).append("' ");
    }

    return sql.toString();
  }

  private void appendOptionalConditions(
      StringBuilder sql, String grupId, String peunId, String unidId,
      String progId, String niedId, String codMateria, String duplicado) {
    if (duplicado == null) {
      sql.append("AND GRSE.GRUP_ID IS NULL AND GRUP.GRUP_ID = 582149 ");
    }
    if (peunId != null) {
      sql.append("AND GRUP.PEUN_ID IN ('").append(peunId).append("') ");
    } else {
      sql.append("AND GRUP.PEUN_ID IN (SELECT MAX(PEUN_ID) FROM ACADEMICO.PERIODOUNIVERSIDAD "
          + "WHERE TPPA_ID = 5 AND SYSDATE BETWEEN PEUN_FECHAINICIO AND PEUN_FECHAFIN) ");
    }
    if (grupId != null) {
      sql.append("AND GRUP.GRUP_ID='").append(grupId).append("' ");
    }
    if (niedId != null) {
      sql.append("AND NIED.NIED_ID=").append(niedId).append(" ");
    }
    if (unidId != null) {
      sql.append("AND UNID.UNID_ID='").append(unidId).append("' ");
    }
    if (progId != null) {
      sql.append("AND PROG.PROG_ID='").append(progId).append("' ");
    }
    if (codMateria != null) {
      sql.append("AND MATE.MATE_CODIGOMATERIA='").append(codMateria).append("' ");
    }
  }

  private GruposPlataformaVo mapGruposPlataforma(ResultSet rs) throws SQLException {
    GruposPlataformaVo gruposPlataformaVo = new GruposPlataformaVo();
    gruposPlataformaVo.setSemoId(rs.getString("SEMO_ID"));
    gruposPlataformaVo.setUnidad(rs.getString("UNIDAD"));
    gruposPlataformaVo.setGrupId(rs.getString("GRUP_ID"));
    gruposPlataformaVo.setPeunId(rs.getString("PEUN_ID"));
    gruposPlataformaVo.setSemoIdmoodle(rs.getString("SEMO_IDMOODLE"));
    gruposPlataformaVo.setMateCodigomateria(rs.getString("MATE_CODIGOMATERIA"));
    gruposPlataformaVo.setGrupNombre(rs.getString("GRUP_NOMBRE"));
    gruposPlataformaVo.setUnidId(rs.getString("UNID_ID"));
    gruposPlataformaVo.setNombrelargo(rs.getString("NOMBRELARGO"));
    gruposPlataformaVo.setNiedId(rs.getString("NIED_ID"));
    gruposPlataformaVo.setNiedDescripcion(rs.getString("NIED_DESCRIPCION"));
    gruposPlataformaVo.setGrseIdmoodle(rs.getString("GRSE_IDMOODLE"));
    gruposPlataformaVo.setSemoNombrelargo(rs.getString("SEMO_NOMBRELARGO"));
    gruposPlataformaVo.setSemoNombrecorto(rs.getString("SEMO_NOMBRECORTO"));
    gruposPlataformaVo.setCadiId(rs.getString("CADI_ID"));
    gruposPlataformaVo.setCaiId(rs.getString("CAI_ID"));
    gruposPlataformaVo.setCateId(rs.getString("CATE_ID"));
    return gruposPlataformaVo;
  }


}
