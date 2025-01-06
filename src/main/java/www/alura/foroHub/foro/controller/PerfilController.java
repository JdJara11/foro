package www.alura.foroHub.foro.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import www.alura.foroHub.foro.domain.profile.DatosPerfil;
import www.alura.foroHub.foro.domain.profile.DatosRegistroPerfil;
import www.alura.foroHub.foro.domain.profile.Perfil;
import www.alura.foroHub.foro.domain.profile.PerfilRepo;

import java.net.URI;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {
    @Autowired
    PerfilRepo perfilRepo;

    @GetMapping()
    public ResponseEntity<Page<DatosPerfil>> obtenerPerfiles(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(perfilRepo.findAll(paginacion).map(DatosPerfil::new));
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<DatosPerfil> guardaPerfil(@RequestBody @Valid DatosRegistroPerfil datosRegistraPerfil, UriComponentsBuilder uriComponentsBuilder){

        Perfil perfil = new Perfil(datosRegistraPerfil);
        System.out.println(perfil.getId());
        perfilRepo.save(perfil);
        URI uri = uriComponentsBuilder.path("/perfiles/{id}").buildAndExpand(perfil.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosPerfil(perfil.getId(), perfil.getNombre()));
    }

    @Override
    public String toString() {
        return "PerfilController: " +
                "perfilRepository=" + perfilRepo;
    }
}
