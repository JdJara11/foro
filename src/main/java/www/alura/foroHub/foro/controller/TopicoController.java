package www.alura.foroHub.foro.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import www.alura.foroHub.foro.domain.course.Curso;
import www.alura.foroHub.foro.domain.course.CursoServicio;
import www.alura.foroHub.foro.domain.topic.*;
import www.alura.foroHub.foro.domain.user.Usuario;
import www.alura.foroHub.foro.domain.user.UsuarioServicio;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    TopicoRepo topicoRepo;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    CursoServicio cursoServicio;

    @Autowired
    TopicoServicio topicoServicio;

    @GetMapping()
    public ResponseEntity<Page<DatosTopico>> obtenerTopicos(
            @RequestParam(required = false) String nombreCurso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, page = 0, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {

        Page<Topico> topicos;

        if (nombreCurso != null && anio != null) {
            topicos = topicoRepo.findByCursoNombreAndFechaCreacionYear(nombreCurso, anio, paginacion);
        } else if (nombreCurso != null) {
            topicos = topicoRepo.findByCursoNombre(nombreCurso, paginacion);
        } else if (anio != null) {
            topicos = topicoRepo.findByFechaCreacionYear(anio, paginacion);
        } else {
            topicos = topicoRepo.findAll(paginacion);
        }

        Page<DatosTopico> datosSalida = topicos.map(t -> new DatosTopico(
                t.getId(),
                t.getTitulo(),
                t.getMensaje(),
                t.getFechaCreacion(),
                t.getEstado(),
                t.getAutor().getId(),
                t.getCurso().getId()
        ));

        return ResponseEntity.ok(datosSalida);
    }


    @PostMapping
    @Transactional
    public ResponseEntity<DatosTopico> registraTopico(
            @RequestBody @Valid DatosRegistroTopico datosRegistraTopico,
            UriComponentsBuilder uriComponentsBuilder){

        topicoServicio.validarTopico(datosRegistraTopico.titulo(), datosRegistraTopico.mensaje());


        Usuario autor = usuarioServicio.buscarAutor(datosRegistraTopico.idAutor());
        Curso curso = cursoServicio.buscarCurso(datosRegistraTopico.idCurso());
        Topico topico = new Topico();
        topico.agregaFechaEstadoCursoAutor(curso, autor, datosRegistraTopico.mensaje(), datosRegistraTopico.titulo());
        topicoRepo.save(topico);
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getAutor().getId(),
                topico.getCurso().getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosTopico> obtenTopico(@PathVariable Long id){
        Optional<Topico> topicoOpcional = topicoRepo.findById(id);
        if (topicoOpcional.isPresent()) {
            Topico topico = topicoOpcional.get();
            return ResponseEntity.ok(new DatosTopico(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getFechaCreacion(),
                    topico.getEstado(),
                    topico.getAutor().getId(),
                    topico.getCurso().getId()));

        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosTopico> actualizaTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datosActualizaTopico,
            UriComponentsBuilder uriComponentsBuilder){

        topicoServicio.validarTopico(datosActualizaTopico.titulo(), datosActualizaTopico.mensaje());


        Optional<Topico> topicoOptional = topicoRepo.findById(id);
        Topico topico = new Topico();
        if (topicoOptional.isPresent()){
            topico = topicoOptional.get();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        if (datosActualizaTopico.mensaje()==null && datosActualizaTopico.titulo()==null ){
            return ResponseEntity.status(400).body(null);
        } else if (datosActualizaTopico.mensaje()==null) {
            topico.cambiaTitulo( datosActualizaTopico.titulo());
        } else if (datosActualizaTopico.titulo()==null) {
            topico.cambiaMensaje(datosActualizaTopico.mensaje());
        }else{
            topico.cambiaMensajeTitulo(datosActualizaTopico.mensaje(), datosActualizaTopico.titulo());
        }

        topicoRepo.save(topico);

        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getAutor().getId(),
                topico.getCurso().getId()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminaTopico(@PathVariable Long id){
        Optional<Topico> topicoOptional = topicoRepo.findById(id);
        Topico topico = new Topico();
        if (topicoOptional.isPresent()){
            topicoRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

    }
}
