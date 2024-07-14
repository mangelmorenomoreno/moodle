package com.ucundinamarca.modules.instanciamoodle.dataproviders.jpa;


import com.ucundinamarca.crosscutting.persistence.repository.InstanciaMoodleRepository;
import com.ucundinamarca.modules.instanciamoodle.dataproviders.IInstanciaMoodleDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * InstanciaMoodleDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */

@Log4j2
@Service
public class InstanciaMoodleDataProviders implements IInstanciaMoodleDataProviders {

    @Autowired
    private InstanciaMoodleRepository instanciaMoodleRepository;

}
