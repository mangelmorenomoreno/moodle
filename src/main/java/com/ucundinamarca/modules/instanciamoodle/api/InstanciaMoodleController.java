package com.ucundinamarca.modules.instanciamoodle.api;


import com.ucundinamarca.crosscutting.domain.constants.ControllerConstants;
import com.ucundinamarca.modules.instanciamoodle.usecase.InstanciaMoodleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * InstanciaMoodleController.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */


@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping(ControllerConstants.INSTANCIA_URL)
public class InstanciaMoodleController {

    @Autowired
    private InstanciaMoodleService instanciaMoodleService;





}
