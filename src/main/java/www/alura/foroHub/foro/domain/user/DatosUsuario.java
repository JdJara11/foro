package www.alura.foroHub.foro.domain.user;

import www.alura.foroHub.foro.domain.profile.DatosPerfil;

import java.util.List;

public record DatosUsuario(Long id, String nombre, List<DatosPerfil> perfiles) {
}
