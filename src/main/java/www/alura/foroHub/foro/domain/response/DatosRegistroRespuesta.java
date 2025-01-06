package www.alura.foroHub.foro.domain.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
        @NotBlank String mensaje,
        @NotNull Long idTopico,
        @NotNull Long idUsuario,
        String solucion
) {
}
