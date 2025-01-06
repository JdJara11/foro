package www.alura.foroHub.foro.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import www.alura.foroHub.foro.domain.response.DatosRegistroRespuesta;
import www.alura.foroHub.foro.domain.response.DatosRespuesta;
import www.alura.foroHub.foro.domain.response.Respuesta;
import www.alura.foroHub.foro.domain.response.RespuestaRepo;
import www.alura.foroHub.foro.domain.topic.Topico;
import www.alura.foroHub.foro.domain.topic.TopicoRepo;
import www.alura.foroHub.foro.domain.user.Usuario;
import www.alura.foroHub.foro.domain.user.UsuarioRepo;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {
    @Autowired
    UsuarioRepo usuarioRepo;

    @Autowired
    TopicoRepo topicoRepo;

    @Autowired
    RespuestaRepo respuestaRepo;

    @Transactional
    @PostMapping
    ResponseEntity<DatosRespuesta> guardarRespuesta(
            @RequestBody @Valid DatosRegistroRespuesta datosRegistraRespuesta,
            UriComponentsBuilder uriComponentsBuilder){
        System.out.println("Datos recibidos " + datosRegistraRespuesta);

        Optional<Topico> topicoOpcional = topicoRepo.findById(datosRegistraRespuesta.idTopico());
        Topico topico = new Topico();
        if (topicoOpcional.isPresent()){
            topico = topicoOpcional.get();
        }else {
            throw new RuntimeException("Topico inexistente");
        }

        Optional<Usuario> usuarioOpcional = usuarioRepo.findById(datosRegistraRespuesta.idUsuario());
        Usuario usuario = new Usuario();
        if (usuarioOpcional.isPresent()){
            usuario = usuarioOpcional.get();
        }else {
            throw new RuntimeException("Autor inexistente");
        }

        System.out.println("Topico "+ topico.toString());
        System.out.println(("Usuario " + usuario.toString()));

        Respuesta respuesta = new Respuesta(null,
                datosRegistraRespuesta.mensaje(),
                LocalDateTime.now(),
                topico,
                usuario,
                null);
        respuestaRepo.save(respuesta);

        URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosRespuesta(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getTopico().getId(),
                respuesta.getAutor().getId(),
                respuesta.getSolucion()
        ));
    }
}
