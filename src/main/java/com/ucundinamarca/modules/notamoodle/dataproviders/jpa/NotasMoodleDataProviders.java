package com.ucundinamarca.modules.notamoodle.dataproviders.jpa;


import com.ucundinamarca.crosscutting.persistence.repository.NotasMoodleRepository;
import com.ucundinamarca.modules.notamoodle.dataproviders.INotasMoodleDataProviders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * NotasMoodleDataProviders.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 13-07-2024
 */

@Log4j2
@Service
public class NotasMoodleDataProviders implements INotasMoodleDataProviders {

    @Autowired
    private NotasMoodleRepository notasMoodleRepository;


}
