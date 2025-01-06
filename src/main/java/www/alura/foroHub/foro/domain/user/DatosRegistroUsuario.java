package www.alura.foroHub.foro.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import www.alura.foroHub.foro.domain.profile.DatosRegistroPerfil;

import java.util.List;

public record DatosRegistroUsuario(
        Long id,
        @NotBlank String nombre,
        @NotBlank @Email @JsonProperty("correo_electronico") String correoElectronico,
        @NotBlank @Pattern(regexp = ".{6,}",
                message = "La contraseña debe tener al menos 6 caracteres.") String contrasena,
        List<DatosRegistroPerfil> perfiles
) {
}
