package com.ucundinamarca.modules.gruposemilla.dataproviders;


import com.ucundinamarca.crosscutting.domain.dto.moodle.GruposPlataformaVo;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.GrupoSemilla;
import java.util.List;

/**
 * IGrupoSemillaDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
public interface IgrupoSemillaDataProviders {

  GrupoSemilla save(GrupoSemilla grupoSemilla);

  List<GruposPlataformaVo> listarGruposPlataformamsivo(
      String grupId, String peunId, String unidId, String progId, String niedId,
      String codMateria, String tipoCadi, String duplicado) throws Exception;
}
