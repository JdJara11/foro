package www.alura.foroHub.foro.domain.course;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroCurso(
        Long id,
        @NotBlank String nombre,
        @NotBlank String categoria
) {
}
