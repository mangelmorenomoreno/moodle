package com.ucundinamarca.modules.matriculamoodle.api;


import com.ucundinamarca.crosscutting.domain.constants.ControllerConstants;
import com.ucundinamarca.modules.matriculamoodle.usecase.MatriculaMoodleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MatriculaMoodleController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */

@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.MATRICULAMOODLE_URL)
public class MatriculaMoodleController {

  @Autowired
  private MatriculaMoodleService matriculaMoodleService;


}
