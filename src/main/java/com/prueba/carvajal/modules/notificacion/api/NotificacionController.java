package com.prueba.carvajal.modules.notificacion.api;

import com.prueba.carvajal.crosscutting.domain.constants.ControllerConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * NotificacionController
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.NOTIFICACION_URL)
public class NotificacionController {
}
