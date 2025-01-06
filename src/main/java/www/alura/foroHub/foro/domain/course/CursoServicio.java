package www.alura.foroHub.foro.domain.course;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CursoServicio {
    @Autowired
    CursoRepo cursoRepo;


    public Curso buscarCurso(@NotNull Long id) {
        Optional<Curso> cursoOpcional = cursoRepo.findById(id);
        if (cursoOpcional.isPresent()){
            return cursoOpcional.get();
        }else {
            throw new RuntimeException("Curso inexistente");
        }
    }
}
