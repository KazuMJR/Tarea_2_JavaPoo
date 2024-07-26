import java.util.*;

public class Alumno extends Persona {
    private String carrera;
    private List<Curso> cursos;

    public Alumno(String nombre, String id, String carrera) {
        super(nombre, id);
        this.carrera = carrera;
        this.cursos = new ArrayList<>();
    }

    public String getCarrera() {
        return carrera;
    }

    public void inscribirCurso(Curso curso) {
        if (!cursos.contains(curso)) {
            cursos.add(curso);
            System.out.println("Inscrito en el curso: " + curso.getNombre());
        } else {
            System.out.println("Ya está inscrito en el curso: " + curso.getNombre());
        }
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Alumno: " + getNombre() + ", ID: " + getId() + ", Carrera: " + carrera);
        System.out.println("Cursos Inscritos:");
        for (Curso curso : cursos) {
            System.out.println(" - " + curso.getNombre());
        }
    }
}
