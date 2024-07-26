import java.util.*;

public class Catedratico extends Persona {
    private String profesion;
    private List<Curso> cursos;

    public Catedratico(String nombre, String id, String profesion) {
        super(nombre, id);
        this.profesion = profesion;
        this.cursos = new ArrayList<>();
    }

    public String getProfesion() {
        return profesion;
    }

    public void asignarCurso(Curso curso) {
        if (!cursos.contains(curso)) {
            cursos.add(curso);
            System.out.println("Asignado al curso: " + curso.getNombre());
        } else {
            System.out.println("Ya está asignado a este curso: " + curso.getNombre());
        }
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Catedrático: " + getNombre() + ", ID: " + getId() + ", Profesión: " + profesion);
        System.out.println("Cursos Asignados:");
        for (Curso curso : cursos) {
            System.out.println(" - " + curso.getNombre());
        }
    }
}
