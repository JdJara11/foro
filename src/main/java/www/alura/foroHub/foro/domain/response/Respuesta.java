package www.alura.foroHub.foro.domain.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import www.alura.foroHub.foro.domain.topic.Topico;
import www.alura.foroHub.foro.domain.user.Usuario;

import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;
    private String solucion;

    public Respuesta() {
    }

    public Long getId() {
        return id;
    }


    public Respuesta(Long id, String mensaje, LocalDateTime fechaCreacion, Topico topico, Usuario autor, String solucion) {
        this.id = id;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.topico = topico;
        this.autor = autor;
        this.solucion = solucion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public String getSolucion() {
        return solucion;
    }

    public Topico getTopico() {
        return topico;
    }
}
