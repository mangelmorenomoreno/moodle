package com.ucundinamarca.modules.gruposemilla.dataproviders.jpa;

import com.ucundinamarca.crosscutting.domain.dto.moodle.GruposPlataformaVo;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.GrupoSemilla;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.repository.GrupoSemillaRepository;
import com.ucundinamarca.crosscutting.persistence.reporteador.repository.GruposPlataformaRepository;
import com.ucundinamarca.modules.gruposemilla.dataproviders.IgrupoSemillaDataProviders;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * GrupoSemillaDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@Service
public class GrupoSemillaDataProviders implements IgrupoSemillaDataProviders {

  @Autowired
  private GrupoSemillaRepository jpaGrupoSemillaRepository;

  @Autowired
  private GruposPlataformaRepository gruposPlataformaRepository;


  @Override
  public GrupoSemilla save(GrupoSemilla grupoSemilla) {
    return jpaGrupoSemillaRepository.save(grupoSemilla);
  }

  @Override
  public List<GruposPlataformaVo> listarGruposPlataformamsivo(
      String grupId, String peunId, String unidId, String progId,
      String niedId, String codMateria, String tipoCadi, String duplicado) throws Exception {
    return gruposPlataformaRepository.listarGruposPlataformamsivo(
        grupId, peunId, unidId, progId, niedId, codMateria, tipoCadi, duplicado);
  }
}
