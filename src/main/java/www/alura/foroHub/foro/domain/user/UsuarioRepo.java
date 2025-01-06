package www.alura.foroHub.foro.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepo extends JpaRepository<Usuario,Long> {
    UserDetails findByNombre(String username);
}
