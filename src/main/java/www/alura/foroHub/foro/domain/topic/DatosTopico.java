package www.alura.foroHub.foro.domain.topic;

import java.time.LocalDateTime;

public record DatosTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String status,
        Long idUsuario,
        Long idCurso
) {
}
