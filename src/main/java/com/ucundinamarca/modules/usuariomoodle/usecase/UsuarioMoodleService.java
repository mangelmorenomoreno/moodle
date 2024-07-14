package com.ucundinamarca.modules.usuariomoodle.usecase;

import com.ucundinamarca.modules.reporteador.dataproviders.IReporteadorDataProviders;
import com.ucundinamarca.modules.usuariomoodle.dataproviders.IUsuarioMoodleDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * UsuarioMoodleService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@Service
public class UsuarioMoodleService {


    @Autowired
    private IUsuarioMoodleDataProviders iUsuarioMoodleDataProviders;

    @Autowired
    private IReporteadorDataProviders iReporteadorDataProviders;


}
