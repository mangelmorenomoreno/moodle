package com.ucundinamarca.modules.usuariomoodle.dataproviders.jpa;

import com.ucundinamarca.crosscutting.domain.dto.moodle.DocentesVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.EstudiantesVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.PeriodoUniversidadVo;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.UsuarioMoodle;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.repository.UsuarioMoodleRepository;
import com.ucundinamarca.crosscutting.persistence.reporteador.repository.DocenteMoodleReporteador;
import com.ucundinamarca.crosscutting.persistence.reporteador.repository.EstudiantesRepository;
import com.ucundinamarca.crosscutting.persistence.reporteador.repository.PeriodoUniversidadRepository;
import com.ucundinamarca.modules.usuariomoodle.dataproviders.IusuarioMoodleDataProviders;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UsuarioMoodleDataProvider.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Service
@Log4j2
public class UsuarioMoodleDataProvider implements IusuarioMoodleDataProviders {

  @Autowired
  public UsuarioMoodleRepository jpaUsuarioMoodleRepository;

  @Autowired
  public EstudiantesRepository estudiantesRepository;

  @Autowired
  public DocenteMoodleReporteador docenteMoodleReporteador;

  @Autowired
  public PeriodoUniversidadRepository periodoUniversidadRepository;


  @Override
  public List<EstudiantesVo> listarEstudianteSin(
      String niedId, String pegeId, String peunId,
      String pegeDocumentoidentidad, String facultad,
      String siteId, String cateId, String instId,
      String tiumId, String progId, String unidId
  ) {
    return estudiantesRepository.listarEstudianteSin(niedId, pegeId, peunId,
        pegeDocumentoidentidad, facultad, siteId,
        cateId, instId, tiumId, progId, unidId);
  }

  @Override
  public List<UsuarioMoodle> saveAll(List<UsuarioMoodle> usuarioMoodle) {
    return jpaUsuarioMoodleRepository.saveAll(usuarioMoodle);
  }

  @Override
  public UsuarioMoodle save(UsuarioMoodle usuarioMoodle) {
    return jpaUsuarioMoodleRepository.save(usuarioMoodle);
  }

  @Override
  public PeriodoUniversidadVo listPeriodoUniversidad() {
    return periodoUniversidadRepository.findAll();
  }

  @Override
  public List<DocentesVo> registroListarDocente(
      String pegeId, String documento, String peunId,
      String usuario, String programa, String unidad, String instancia) throws Exception {
    return docenteMoodleReporteador.registroListarDocente(
        pegeId, documento, peunId, usuario, programa, unidad, instancia);
  }


}
