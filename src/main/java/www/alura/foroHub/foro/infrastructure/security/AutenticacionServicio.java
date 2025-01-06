package www.alura.foroHub.foro.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import www.alura.foroHub.foro.domain.user.UsuarioRepo;

@Service
public class AutenticacionServicio implements UserDetailsService {
    @Autowired
    UsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepo.findByNombre(username);
    }
}
