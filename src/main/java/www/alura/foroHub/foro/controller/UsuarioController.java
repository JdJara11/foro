package www.alura.foroHub.foro.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import www.alura.foroHub.foro.domain.profile.DatosPerfil;
import www.alura.foroHub.foro.domain.profile.Perfil;
import www.alura.foroHub.foro.domain.profile.PerfilRepo;
import www.alura.foroHub.foro.domain.user.DatosRegistroUsuario;
import www.alura.foroHub.foro.domain.user.DatosUsuario;
import www.alura.foroHub.foro.domain.user.Usuario;
import www.alura.foroHub.foro.domain.user.UsuarioRepo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioRepo usuarioRepo;

    @Autowired
    PerfilRepo perfilRepo;

    @GetMapping
    public ResponseEntity<Page<DatosUsuario>> obtenerUsuarios(Pageable paginacion){
        return  ResponseEntity.ok(usuarioRepo.findAll(paginacion)
                .map(u -> new DatosUsuario(
                        u.getId(),
                        u.getNombre(),
                        u.getPerfiles()
                                .stream().map(p -> new DatosPerfil(
                                        p.getId(),
                                        p.getNombre())).toList())));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> guardaUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistraUsuario, UriComponentsBuilder uriComponentsBuilder) {
        Optional<Perfil> perfilOpcional = perfilRepo.findById(1L);

        if (perfilOpcional.isPresent()) {

            Usuario usuario = new Usuario(datosRegistraUsuario);
            List<Perfil> perfiles = new ArrayList<>();
            perfiles.add(perfilOpcional.get());
            usuario.setPerfiles(perfiles);
            usuarioRepo.save(usuario);
            URI uri = uriComponentsBuilder.path("/perfiles/{id}").buildAndExpand(usuario.getId()).toUri();
            return ResponseEntity.created(uri).body(new DatosUsuario(usuario.getId(), usuario.getNombre(), (List<DatosPerfil>) usuario.getPerfiles().stream().map(p -> new DatosPerfil(p.getId(), p.getNombre())).toList()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil inexistente");
        }
    }
}
