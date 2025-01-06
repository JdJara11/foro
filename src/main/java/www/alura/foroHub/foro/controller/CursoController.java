package www.alura.foroHub.foro.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import www.alura.foroHub.foro.domain.course.Curso;
import www.alura.foroHub.foro.domain.course.CursoRepo;
import www.alura.foroHub.foro.domain.course.DatosCurso;
import www.alura.foroHub.foro.domain.course.DatosRegistroCurso;

import java.net.URI;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    CursoRepo cursoRepo;

    @Transactional
    @PostMapping
    public ResponseEntity<DatosCurso> guardaCurso(@RequestBody @Valid DatosRegistroCurso datosRegistraCurso,
                                                  UriComponentsBuilder uriComponentsBuilder){
        Curso curso = cursoRepo.save(new Curso(datosRegistraCurso.id(),
                datosRegistraCurso.nombre(),
                datosRegistraCurso.categoria()));
        URI uri = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosCurso(curso.getId(), curso.getNombre(), curso.getCategoria()));
    }

    @GetMapping
    public ResponseEntity<Page<DatosCurso>> obtenerDatos(Pageable paginacion){
        return ResponseEntity.ok( cursoRepo.findAll(paginacion)
                .map(c -> new DatosCurso(
                        c.getId()
                        , c.getNombre()
                        , c.getCategoria())));
    }

}
