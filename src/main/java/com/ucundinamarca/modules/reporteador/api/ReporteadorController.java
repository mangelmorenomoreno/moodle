package com.ucundinamarca.modules.reporteador.api;

import com.ucundinamarca.crosscutting.domain.constants.ControllerConstants;
import com.ucundinamarca.modules.reporteador.usecase.ReporteadorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * ReporteadorController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.REPORTEADOR_URL)
public class ReporteadorController {


  @Autowired
  private ReporteadorService reporteadorService;



  /*  @ApiOperation(
            value = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_VALUE,
            nickname = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_NICK,
            notes = ApiDocumentationConstant.CONSULTAR_USUARIO_CORREO_NOTES,
            response = ResponseEntity.class)
    @GetMapping(ControllerConstants.INFORMACION_USUARIO_O_CORREO_URL)
    public ResponseEntity<IrestResponse<List<Usuario>>> findByEmailOrNombre(
            @PathVariable String valor) throws Exception {
        return ResponseEntityUtil.createSuccessfulResponse(
                ResponseValueConstants.SUCCESS,
                //usuarioService.findByNombreAndCorreoLike(valor),
                MessageCodes.PROCESS_SUCCES.getMessage(),
                MessageCodes.PROCESS_SUCCES.name());
    }

   */


}
