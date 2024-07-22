package com.ucundinamarca.modules.usuariomoodle.dataproviders;

import com.ucundinamarca.crosscutting.domain.dto.moodle.EstudiantesVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.PeriodoUniversidadVo;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.UsuarioMoodle;
import java.util.List;

/**
 * IUsuarioMoodleDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
public interface IusuarioMoodleDataProviders {

  List<EstudiantesVo> listarEstudianteSin(String niedId, String pegeId, String peunId,
                                          String pegeDocumentoidentidad, String facultad,
                                          String siteId, String cateId, String instId,
                                          String tiumId, String progId, String unidId);

  List<UsuarioMoodle> saveAll(List<UsuarioMoodle> usuarioMoodle);

  UsuarioMoodle save(UsuarioMoodle usuarioMoodle);

  PeriodoUniversidadVo listPeriodoUniversidad();

}
