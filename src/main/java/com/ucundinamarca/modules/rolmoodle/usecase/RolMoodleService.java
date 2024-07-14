package com.ucundinamarca.modules.rolmoodle.usecase;


import com.ucundinamarca.modules.reporteador.dataproviders.IReporteadorDataProviders;
import com.ucundinamarca.modules.rolmoodle.dataproviders.IRolMoodleDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RolMoodleService.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@Service
public class RolMoodleService {

    @Autowired
    private IRolMoodleDataProviders iRolMoodleDataProviders;

    @Autowired
    private IReporteadorDataProviders iReporteadorDataProviders;

}
