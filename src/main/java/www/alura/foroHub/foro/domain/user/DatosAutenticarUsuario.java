package www.alura.foroHub.foro.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosAutenticarUsuario(
        @NotBlank String nombre,
        @NotBlank @Pattern(regexp = ".{6,}",
                message = "La contraseña debe tener almenos 6 caracteres.")
        String contrasena
) {
}
