package com.ucundinamarca.modules.publicacion.dataproviders.jpa;

import com.ucundinamarca.crosscutting.persistence.entity.Publicacion;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;
import com.ucundinamarca.crosscutting.persistence.repository.PublicacionRepository;
import com.ucundinamarca.modules.publicacion.dataproviders.IpublicacionDataProvider;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * PublicacionDataProvider.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@Log4j2
@Service
public class PublicacionDataProvider implements IpublicacionDataProvider {

  @Autowired
  private PublicacionRepository publicacionRepository;



  @Override
  public Publicacion save(Publicacion publicacion)  {

    return publicacionRepository.save(publicacion);
  }

  @Override
  public List<Publicacion> findAll() {
    return publicacionRepository.findAllOrderByFechaPublicacionDesc();
  }

  @Override
  public Publicacion update(Publicacion publicacion) {
    return publicacionRepository.save(publicacion);
  }

  @Override
  public Boolean delete(Publicacion publicacion) {
    publicacionRepository.delete(publicacion);
    return true;
  }

  @Override
  public List<Publicacion> findByUsuario(Usuario usuario) {
    return publicacionRepository.findByUsuario(usuario);
  }

  @Override
  public Publicacion findById(Integer id) {
    return publicacionRepository.findById(id).get();
  }
}
