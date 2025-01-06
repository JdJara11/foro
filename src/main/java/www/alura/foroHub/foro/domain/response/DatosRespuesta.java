package www.alura.foroHub.foro.domain.response;

import java.time.LocalDateTime;

public record DatosRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        Long idTopico,
        Long idUsuario,
        String solucion
) {
}
