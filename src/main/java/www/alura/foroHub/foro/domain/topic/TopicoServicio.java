package www.alura.foroHub.foro.domain.topic;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TopicoServicio {
    @Autowired
    TopicoRepo topicoRepo;

    public void validarTopico(String titulo, String mensaje) {

        Optional<Topico> existente = topicoRepo.findByTituloAndMensaje(titulo, mensaje);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un tópico con estos datos");
        }
    }
}
