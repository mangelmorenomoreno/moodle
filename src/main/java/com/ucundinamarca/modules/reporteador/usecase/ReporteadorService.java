package com.ucundinamarca.modules.reporteador.usecase;

import com.ucundinamarca.modules.reporteador.dataproviders.IReporteadorDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * ReporteadorService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@Service
public class ReporteadorService {

    @Autowired
    private IReporteadorDataProviders iReporteadorDataProviders;

}
