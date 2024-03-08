package com.prueba.carvajal.modules.usuario.usecase;

import com.prueba.carvajal.crosscutting.domain.dto.user.BasicInformationUserDTO;
import com.prueba.carvajal.crosscutting.domain.dto.user.UserModelMacro;
import com.prueba.carvajal.crosscutting.persistence.entity.Credencial;
import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import com.prueba.carvajal.crosscutting.utils.TokenGenerator;
import com.prueba.carvajal.modules.credencial.dataproviders.ICredencialDataProvider;
import com.prueba.carvajal.modules.sendmail.usecase.message.MessageSenderService;
import com.prueba.carvajal.modules.usuario.dataproviders.IUsuarioDataProvider;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * UsuarioService
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 7-03-2024
 */

@Log4j2
@Service
public class UsuarioService {

  @Value("${integration.queues.activate}")
  private String activate;

  @Autowired
  private MessageSenderService messageSenderService;

  @Autowired
  private ICredencialDataProvider iCredencialDataProvider;

  @Autowired
  private IUsuarioDataProvider iUsuarioDataProvider;

  public Usuario getFindByUserId(String token) throws Exception {

    return iUsuarioDataProvider.findByUserId(
        Integer.valueOf(TokenGenerator.getIdUserFromToken(token))
    );
  }

  public Usuario findByCorreoElectronico(String correoElectronico) throws Exception {
    return iUsuarioDataProvider.findByCorreoElectronico(correoElectronico);
  }

  public Boolean save(BasicInformationUserDTO basicInformationUserDTO) throws Exception {

    Usuario usuario = iUsuarioDataProvider.save(Usuario.builder()
        .nombre(basicInformationUserDTO.getNombre())
        .apellido(basicInformationUserDTO.getApellido())
        .correoElectronico(basicInformationUserDTO.getCorreo())
        .build());

    iCredencialDataProvider.save(Credencial.builder()
        .usuario(usuario)
        .tokenResetPassword(TokenGenerator.createTokenMacroChangePassword(usuario)).build());

    messageSenderService.sendMessage(activate, UserModelMacro.builder()
        .userId(usuario.getUserId())
        .email(usuario.getCorreoElectronico())
        .userName(usuario.getNombre() + " " + usuario.getApellido())
        .build());

    return usuario.getUserId() != null;
  }

  public Boolean update(BasicInformationUserDTO basicInformationUserDTO, String token) throws Exception {
    return iUsuarioDataProvider.save(Usuario.builder()
        .userId(Integer.valueOf(TokenGenerator.getIdUserFromToken(token)))
        .nombre(basicInformationUserDTO.getNombre())
        .apellido(basicInformationUserDTO.getApellido())
        .correoElectronico(basicInformationUserDTO.getCorreo())
        .build()).getUserId() != null;
  }

  public List<Usuario> findByNombreAndCorreoLike(String valor) {
    return iUsuarioDataProvider.findByNombreAndCorreoElectronicoSimilar(valor);
  }


}
