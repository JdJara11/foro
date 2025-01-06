package www.alura.foroHub.foro.domain.profile;

public record DatosPerfil(Long id, String nombre) {
    public DatosPerfil(Perfil perfil) {
        this(perfil.getId(), perfil.getNombre());
    }
}
