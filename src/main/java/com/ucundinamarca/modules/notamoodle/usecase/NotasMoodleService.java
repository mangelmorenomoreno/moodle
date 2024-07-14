package com.ucundinamarca.modules.notamoodle.usecase;

import com.ucundinamarca.modules.notamoodle.dataproviders.INotasMoodleDataProviders;
import com.ucundinamarca.modules.reporteador.dataproviders.IReporteadorDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * NotasMoodleService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@Service
public class NotasMoodleService {

    @Autowired
    private INotasMoodleDataProviders iNotasMoodleDataProviders;

    @Autowired
    private IReporteadorDataProviders iReporteadorDataProviders;

}
