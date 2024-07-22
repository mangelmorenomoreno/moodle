package com.ucundinamarca.crosscutting.persistence.reporteador.repository;

import com.ucundinamarca.crosscutting.domain.dto.moodle.PeriodoUniversidadVo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing Periodo Universidad data in Reporteador database.
 * Provides method to find all university periods based on specific criteria.
 *
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
public class PeriodoUniversidadRepository {

  @Autowired
  @Qualifier("reporteadorJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  /**
   * Finds all university periods based on specific criteria.
   *
   * @return PeriodoUniversidadVo representing the university period that matches the criteria.
   */
  public PeriodoUniversidadVo findAll() {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT PEUN.PEUN_ID, \n\t");
    sql.append("       PEUN.PEUN_PERIODO, \n\t");
    sql.append("       PEUN.PEUN_ANO \n\t");
    sql.append("FROM ACADEMICO.PERIODOUNIVERSIDAD PEUN \n\t");
    sql.append("WHERE PEUN.TPPA_ID = 5 \n\t");
    sql.append("  AND PEUN.PEUN_ANO >= 2022 \n\t");
    sql.append("  AND PEUN.PEUN_FECHAFINCLASES > SYSDATE \n\t");


    return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<PeriodoUniversidadVo>() {
      @Override
      public PeriodoUniversidadVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        PeriodoUniversidadVo periodoUniversidadVo = new PeriodoUniversidadVo();
        periodoUniversidadVo.setPeunPeriodo(rs.getString("PEUN_PERIODO"));
        periodoUniversidadVo.setPeunAno(rs.getString("PEUN_ANO"));
        periodoUniversidadVo.setPeunId(rs.getString("PEUN_ID"));
        return periodoUniversidadVo;
      }
    });
  }

}
