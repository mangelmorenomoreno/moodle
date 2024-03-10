package com.prueba.carvajal.modules.comentario.api;


import com.prueba.carvajal.crosscutting.domain.constants.ControllerConstants;
import com.prueba.carvajal.modules.comentario.dataproviders.jpa.ComentarioDataProviders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ComentarioController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.COMENTARIO_URL)
public class ComentarioController {

  @Autowired
  private ComentarioDataProviders comentarioDataProviders;


}
