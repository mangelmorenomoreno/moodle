package com.prueba.carvajal.modules.publicacion.api;

import com.prueba.carvajal.crosscutting.domain.constants.ControllerConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PublicacionController
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.PUBLICACION_URL)
public class PublicacionController {
}
