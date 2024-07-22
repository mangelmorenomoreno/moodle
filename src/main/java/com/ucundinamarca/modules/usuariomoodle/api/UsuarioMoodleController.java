package com.ucundinamarca.modules.usuariomoodle.api;

import com.ucundinamarca.crosscutting.domain.constants.ApiDocumentationConstant;
import com.ucundinamarca.crosscutting.domain.constants.ControllerConstants;
import com.ucundinamarca.crosscutting.domain.constants.ResponseValueConstants;
import com.ucundinamarca.crosscutting.domain.dto.moodle.EstudiantesVo;
import com.ucundinamarca.crosscutting.domain.enums.MessageCodes;
import com.ucundinamarca.crosscutting.patterns.IrestResponse;
import com.ucundinamarca.crosscutting.utils.ResponseEntityUtil;
import com.ucundinamarca.modules.usuariomoodle.usecase.UsuarioMoodleService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * UsuarioMoodleController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.USUARIO_URL)
public class UsuarioMoodleController {

  @Autowired
  private UsuarioMoodleService usuarioMoodleService;

  /**
   * Encuentra usuarios por matricular.
   *
   * @return ResponseEntity con la lista de estudiantes sin matricular.
   */
  @ApiOperation(
      value = ApiDocumentationConstant.USUARIO_PREGRADO_VALUE,
      nickname = ApiDocumentationConstant.USUARIO_PREGRADO_NICK,
      notes = ApiDocumentationConstant.USUARIO_PREGRADO_NOTES,
      response = ResponseEntity.class)
  @GetMapping(ControllerConstants.USUARIO_PREGRADO)
  public ResponseEntity<IrestResponse<List<EstudiantesVo>>> findUsuariosPorMatricular() {
    return ResponseEntityUtil.createSuccessfulResponse(
        ResponseValueConstants.SUCCESS,
        usuarioMoodleService.estudiantesSinMatriculaPregrado(),
        MessageCodes.PROCESS_SUCCES.getMessage(),
        MessageCodes.PROCESS_SUCCES.name());
  }


}
