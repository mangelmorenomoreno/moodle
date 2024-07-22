package com.ucundinamarca.crosscutting.persistence.reporteador.repository;

import org.springframework.stereotype.Repository;


/**
 * ReporteadorRepository.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Repository
public class ReporteadorRepository {

   /* @Autowired
    @Qualifier("reporteadorDataSource")
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
