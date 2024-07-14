package com.ucundinamarca.modules.gruposemilla.dataproviders.jpa;

import com.ucundinamarca.crosscutting.persistence.repository.GrupoSemillaRepository;
import com.ucundinamarca.modules.gruposemilla.dataproviders.IGrupoSemillaDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * GrupoSemillaDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */
@Log4j2
@Service
public class GrupoSemillaDataProviders implements IGrupoSemillaDataProviders {

    @Autowired
    private GrupoSemillaRepository grupoSemillaRepository;






}
