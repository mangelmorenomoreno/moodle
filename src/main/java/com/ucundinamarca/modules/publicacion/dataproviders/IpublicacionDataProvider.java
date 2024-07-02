package com.ucundinamarca.modules.publicacion.dataproviders;


import com.ucundinamarca.crosscutting.persistence.entity.Publicacion;
import com.ucundinamarca.crosscutting.persistence.entity.Usuario;

import java.util.List;

/**
 * IPublicacionDataProvider.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */
public interface IpublicacionDataProvider {

  Publicacion save(Publicacion publicacion);

  List<Publicacion> findAll();

  Publicacion update(Publicacion publicacion);

  Boolean delete(Publicacion publicacion);

  List<Publicacion> findByUsuario(Usuario usuario);

  Publicacion findById(Integer id);
}
