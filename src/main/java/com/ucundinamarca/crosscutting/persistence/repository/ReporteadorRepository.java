package com.ucundinamarca.crosscutting.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ReporteadorRepository {

   /* @Autowired
    @Qualifier("reporteadorJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    public List<SomeDTO> findAll() {
        String sql = "SELECT * FROM some_table";
        return jdbcTemplate.query(sql, new RowMapper<SomeDTO>() {
            @Override
            public SomeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                SomeDTO dto = new SomeDTO();
                dto.setId(rs.getLong("id"));
                dto.setName(rs.getString("name"));
                return dto;
            }
        });
    }*/
}
