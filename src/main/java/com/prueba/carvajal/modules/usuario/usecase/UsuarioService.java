package com.prueba.carvajal.modules.usuario.usecase;

import com.prueba.carvajal.crosscutting.domain.dto.user.BasicInformationUserDto;
import com.prueba.carvajal.crosscutting.domain.dto.user.UserModelMacro;
import com.prueba.carvajal.crosscutting.persistence.entity.Credencial;
import com.prueba.carvajal.crosscutting.persistence.entity.Usuario;
import com.prueba.carvajal.crosscutting.utils.TokenGenerator;
import com.prueba.carvajal.modules.credencial.dataproviders.IcredencialDataProvider;
import com.prueba.carvajal.modules.sendmail.usecase.message.MessageSenderService;
import com.prueba.carvajal.modules.usuario.dataproviders.IusuarioDataProvider;
import java.util.Date;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * UsuarioService.
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
  private IcredencialDataProvider icredencialDataProvider;

  @Autowired
  private IusuarioDataProvider iusuarioDataProvider;

  /**
   * Obtiene un usuario basado en el ID de usuario extraído de un token JWT.
   * Este método decodifica un token JWT para obtener el ID de usuario y luego
   * busca y retorna el usuario correspondiente a ese ID en la base de datos.
   * Si el usuario no se encuentra o si ocurre un error en la obtención del ID del token,
   * se lanza una excepción.
   *
   * @param token El token JWT del cual se extrae el ID de usuario.
   * @return El objeto Usuario asociado al ID de usuario extraído del token.
   * @throws Exception Si el ID no se puede extraer del token o si no se encuentra el usuario.
   */
  public Usuario getFindByUserId(String token) throws Exception {

    return iusuarioDataProvider.findByUserId(
      Integer.valueOf(TokenGenerator.getIdUserFromToken(token))
    );
  }

  public Usuario findByCorreoElectronico(String correoElectronico) throws Exception {
    return iusuarioDataProvider.findByCorreoElectronico(correoElectronico);
  }

  /**
   * Guarda un nuevo usuario en la base de datos y envía un mensaje de activación.
   * Este método crea un usuario a partir de un DTO de información básica del usuario,
   * guarda este usuario en la base de datos, crea y guarda una credencial asociada,
   * y finalmente envía un mensaje de activación. Retorna verdadero si el usuario se guarda
   * con éxito, falso en caso contrario.
   *
   * @param basicInformationUserDto El DTO con la información básica del usuario.
   * @return Boolean indicando si el usuario fue guardado con éxito (true) o no (false).
   * @throws Exception Si ocurre algún error durante el proceso de guardado.
   */
  public Boolean save(BasicInformationUserDto basicInformationUserDto) throws Exception {
    try {
      Usuario usuario = createAndSaveUser(basicInformationUserDto);
      createAndSaveCredencial(usuario);
      sendActivationMessage(usuario);
      return usuario.getUserId() != null;
    } catch (Exception e) {
      return false;
    }
  }

  private Usuario createAndSaveUser(BasicInformationUserDto dto) {
    return iusuarioDataProvider.save(Usuario.builder()
                                       .nombre(dto.getNombre())
                                       .apellido(dto.getApellido())
                                       .correoElectronico(dto.getCorreo())
                                       .fechaCreacion(new Date())
                                       .estado(false)
                                       .build());
  }

  private void createAndSaveCredencial(Usuario usuario) {
    icredencialDataProvider.save(Credencial.builder()
                                   .usuario(usuario)
                                   .tokenResetPassword(
                                     TokenGenerator.createTokenMacroChangePassword(usuario))
                                   .build());
  }

  private void sendActivationMessage(Usuario usuario) {
    messageSenderService.sendMessage(activate, UserModelMacro.builder()
                                                 .userId(usuario.getUserId())
                                                 .email(usuario.getCorreoElectronico())
                                                 .userName(usuario.getNombre() + " "
                                                             + usuario.getApellido())
                                                 .build());
  }

  /**
   * Actualiza la información de un usuario existente en la base de datos.
   * Este método recibe un DTO con la nueva información del usuario y un token JWT
   * para identificar al usuario. Actualiza los campos correspondientes del usuario
   * en la base de datos y retorna verdadero si la actualización es exitosa.
   *
   * @param basicInformationUserDto El DTO con la nueva información del usuario.
   * @param token El token JWT que se utiliza para identificar al usuario.
   * @return Boolean indicando si la actualización fue exitosa (true) o no (false).
   * @throws Exception Si ocurre algún error durante el proceso de actualización.
   */
  public Boolean update(BasicInformationUserDto basicInformationUserDto, String token)
      throws Exception {
    return iusuarioDataProvider.save(Usuario.builder()
                                       .userId(
                                         Integer.valueOf(TokenGenerator.getIdUserFromToken(token)))
                                       .nombre(basicInformationUserDto.getNombre())
                                       .apellido(basicInformationUserDto.getApellido())
                                       .correoElectronico(basicInformationUserDto.getCorreo())
                                       .build()).getUserId() != null;
  }

  public List<Usuario> findByNombreAndCorreoLike(String valor) {
    return iusuarioDataProvider.findByNombreAndCorreoElectronicoSimilar(valor);
  }


}
