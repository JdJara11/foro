package www.alura.foroHub.foro.domain.topic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import www.alura.foroHub.foro.domain.course.Curso;
import www.alura.foroHub.foro.domain.response.Respuesta;
import www.alura.foroHub.foro.domain.user.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico() {
    }

    public Topico( String titulo, String mensaje, LocalDateTime fechaCreacion, String status, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.estado = status;
        this.autor = autor;
        this.curso = curso;
    }

    public Topico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, String status, Usuario autor, Curso curso, List<Respuesta> respuestas) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.estado = status;
        this.autor = autor;
        this.curso = curso;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Curso getCurso() {
        return curso;
    }


    @Override
    public String toString() {
        return "Topico{" +
                "id=" + id +
                ", titulo='" + titulo +
                ", mensaje='" + mensaje +
                ", fechaCreacion=" + fechaCreacion +
                ", estado='" + estado +
                ", autor=" + autor +
                ", curso=" + curso +
                '}';
    }

    public void agregaFechaEstadoCursoAutor(Curso curso, Usuario autor, String mensaje, String titulo) {
        this.fechaCreacion= LocalDateTime.now();
        this.estado = "Activo;";
        this.curso = curso;
        this.autor = autor;
        this.mensaje = mensaje;
        this.titulo = titulo;
    }

    public void cambiaMensajeTitulo(String mensaje, String titulo) {
        this.mensaje = mensaje;
        this.titulo = titulo;
    }

    public void cambiaTitulo(String titulo) {
        this.titulo=titulo;
    }

    public void cambiaMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
