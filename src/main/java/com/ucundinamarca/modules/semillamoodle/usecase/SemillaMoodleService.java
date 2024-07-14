package com.ucundinamarca.modules.semillamoodle.usecase;

import com.ucundinamarca.modules.reporteador.dataproviders.IReporteadorDataProviders;
import com.ucundinamarca.modules.semillamoodle.dataproviders.ISemillaMoodleDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * SemillaMoodleService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@Service
public class SemillaMoodleService {

    @Autowired
    private ISemillaMoodleDataProviders iSemillaMoodleDataProviders;

    @Autowired
    private IReporteadorDataProviders iReporteadorDataProviders;

}
