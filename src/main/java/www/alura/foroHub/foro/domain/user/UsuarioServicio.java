package www.alura.foroHub.foro.domain.user;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServicio {
    @Autowired
    UsuarioRepo usuarioRepo;

    public Usuario buscarAutor(@NotNull Long id) {
        Optional<Usuario> autorOpcional = usuarioRepo.findById(id);
        if (autorOpcional.isPresent()){
            return autorOpcional.get();
        }else {
            throw new RuntimeException("Autor no encontrado");
        }
    }
}
