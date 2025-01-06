package www.alura.foroHub.foro.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.alura.foroHub.foro.domain.user.DatosAutenticarUsuario;
import www.alura.foroHub.foro.domain.user.Usuario;
import www.alura.foroHub.foro.infrastructure.security.DatosJwt;
import www.alura.foroHub.foro.infrastructure.security.TokenServicio;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServicio tokenServicio;

    @PostMapping
    public ResponseEntity autentica(@RequestBody @Valid
                                    DatosAutenticarUsuario datosAutenticaUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticaUsuario.nombre(),
                datosAutenticaUsuario.contrasena());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        String jwtToken = tokenServicio.generaToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJwt(jwtToken));
    }
}
