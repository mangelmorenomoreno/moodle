package com.ucundinamarca.modules.gruposemilla.api;


import com.ucundinamarca.crosscutting.domain.constants.ControllerConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * GrupoSemillaController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.GRUPOSEMILLA_URL)
public class GrupoSemillaController {
}
