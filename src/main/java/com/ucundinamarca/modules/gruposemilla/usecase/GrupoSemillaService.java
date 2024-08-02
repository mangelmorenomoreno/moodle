package com.ucundinamarca.modules.gruposemilla.usecase;

import com.ucundinamarca.crosscutting.domain.dto.autentication.ConexionVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.GrupoSemillaVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.GrupoSemillawsVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.GruposPlataformaVo;
import com.ucundinamarca.crosscutting.domain.dto.moodle.RespuestaGruposSemillaVo;
import com.ucundinamarca.crosscutting.persistence.camposdeaprendizaje.entity.GrupoSemilla;
import com.ucundinamarca.crosscutting.utils.Conexion;
import com.ucundinamarca.modules.gruposemilla.dataproviders.IgrupoSemillaDataProviders;
import com.ucundinamarca.modules.gruposemilla.resttemplate.GrupoMoodleRestTemplate;
import com.ucundinamarca.modules.reporteador.dataproviders.IreporteadorDataProviders;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * GrupoSemillaService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@Service
public class GrupoSemillaService {


  @Autowired
  private IgrupoSemillaDataProviders igrupoSemillaDataProviders;


  @Autowired
  private IreporteadorDataProviders ireporteadorDataProviders;

  @Autowired
  private GrupoMoodleRestTemplate grupoMoodleRestTemplate;

  @Autowired
  private Conexion conexion;

  /**
   * <p>
   * Registra grupos de forma masiva en Moodle y los almacena en la base de datos local.
   * Este método obtiene una lista de grupos de la plataforma y, para cada grupo,
   * crea una conexión con Moodle para registrar el grupo utilizando los servicios web.
   * Si el grupo se registra con éxito en Moodle, se almacena en la base de datos local.
   * En caso contrario, se registran los errores correspondientes.
   * </p>
   * <p>
   * El proceso registra tanto los éxitos como los errores en Moodle y en la base de datos local,
   * e informa de ellos a través de los logs.
   * </p>
   *
   * @throws Exception si ocurre un error durante el proceso de registro.
   */
  public void registarGrupoMasivo() throws Exception {
    Timestamp fechacambio = new Timestamp(System.currentTimeMillis());
    List<GruposPlataformaVo> gruposPlataforma =
        igrupoSemillaDataProviders.listarGruposPlataformamsivo(
            null, null, null, null,
            null, null, null, null);
    if (gruposPlataforma != null) {
      int errores = 0;
      int succes = 0;
      int moodlesucces = 0;
      int moodleerror = 0;
      log.info("Inicia proceso");
      for (GruposPlataformaVo grupo : gruposPlataforma) {
        ConexionVo conexionVo = conexion.conexionPregradoCrearGrupos();
        log.info("Procesando grupo: {}", grupo.getGrupId());
        GrupoSemillawsVo grupoSemillawsVo = createGrupoSemillawsVo(grupo);
        RespuestaGruposSemillaVo respuesta = grupoMoodleRestTemplate.crearGrupos(conexionVo,
            grupoSemillawsVo);

        if (respuesta.getId() != null) {
          moodlesucces++;
          log.info("Ejecución con éxito");

          GrupoSemillaVo grupoSemillaVo = createGrupoSemillaVo(grupo, respuesta, "ws", fechacambio);
          GrupoSemilla insertado = igrupoSemillaDataProviders.save(grupoSemilla(grupoSemillaVo));

          if (insertado != null) {
            succes++;
            log.info("Almacenado en la base de datos con éxito");
          } else {
            errores++;
            log.error("Error al almacenar en la base de datos");
          }
        } else {
          moodleerror++;
          log.error("Error en Moodle: {}", respuesta.getException());
        }
      }
    }
  }

  private GrupoSemilla grupoSemilla(GrupoSemillaVo grupoSemillaVo) {
    return GrupoSemilla.builder()
        .grseFechaCambio(grupoSemillaVo.getGrseFechacambio())
        .grseIdMoodle(grupoSemillaVo.getGrseIdmoodle())
        .grseNombreCorto(grupoSemillaVo.getGrseNombrecorto())
        .grseNombreLargo(grupoSemillaVo.getGrseNombrelargo())
        .grseRegistradoPor(grupoSemillaVo.getGrseRegistradopor())
        .grupId(Long.valueOf(grupoSemillaVo.getGrupId()))
        .instId(Long.valueOf(grupoSemillaVo.getInstId()))
        .peunId(Long.valueOf(grupoSemillaVo.getPeunId()))
        .semoId(Long.valueOf(grupoSemillaVo.getSemoId()))
        .build();
  }

  private GrupoSemillawsVo createGrupoSemillawsVo(GruposPlataformaVo grupo) throws Exception {
    GrupoSemillawsVo grupoSemillawsVo = new GrupoSemillawsVo();
    grupoSemillawsVo.setFullname(URLEncoder.encode(grupo.getNombrelargo().toUpperCase(),
        StandardCharsets.UTF_8.toString()));
    grupoSemillawsVo.setShortname(URLEncoder.encode(grupo.getNombrelargo().toUpperCase(),
        StandardCharsets.UTF_8.toString()));
    grupoSemillawsVo.setTemplatecourse(URLEncoder.encode(grupo.getSemoNombrecorto().toUpperCase(),
        StandardCharsets.UTF_8.toString()));
    grupoSemillawsVo.setCourseid(URLEncoder.encode(grupo.getSemoIdmoodle(),
        StandardCharsets.UTF_8.toString()));
    grupoSemillawsVo.setCateId(URLEncoder.encode(grupo.getCateId().toUpperCase(),
        StandardCharsets.UTF_8.toString()));
    return grupoSemillawsVo;
  }

  private GrupoSemillaVo createGrupoSemillaVo(
      GruposPlataformaVo grupo, RespuestaGruposSemillaVo respuesta,
      String codigoUsuario, Timestamp fechacambio) {
    GrupoSemillaVo grupoSemillaVo = new GrupoSemillaVo();
    grupoSemillaVo.setGrseIdmoodle(respuesta.getId());
    grupoSemillaVo.setGrseNombrecorto(grupo.getNombrelargo());
    grupoSemillaVo.setGrseNombrelargo(grupo.getGrupNombre());
    grupoSemillaVo.setGrupId(grupo.getGrupId());
    grupoSemillaVo.setPeunId(grupo.getPeunId());
    grupoSemillaVo.setSemoId(grupo.getSemoId());
    grupoSemillaVo.setInstId(grupo.getNiedId() != null ? grupo.getNiedId() : "1");
    grupoSemillaVo.setGrseRegistradopor(codigoUsuario);
    grupoSemillaVo.setGrseFechacambio(fechacambio);
    return grupoSemillaVo;
  }

}
