import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Menu {
    private List<Alumno> alumnos;
    private List<Catedratico> catedraticos;
    private Map<String, List<Curso>> cursosPorCarrera;

    public Menu() {
        alumnos = new ArrayList<>();
        catedraticos = new ArrayList<>();
        cursosPorCarrera = new HashMap<>();
        inicializarCursos();
    }

    private void inicializarCursos() {
        cursosPorCarrera.put("Ingeniería", Arrays.asList(
                new Curso("Calculo II", "508"),
                new Curso("Estadistica I", "384"),
                new Curso("Programación II", "608")
        ));
        cursosPorCarrera.put("Medicina", Arrays.asList(
                new Curso("Anatomía", "168"),
                new Curso("Fisiología", "184"),
                new Curso("Bioquímica", "857")
        ));
        cursosPorCarrera.put("Derecho", Arrays.asList(
                new Curso("Derecho Civil", "584"),
                new Curso("Derecho Penal", "914"),
                new Curso("Derecho Constitucional", "157")
        ));
        cursosPorCarrera.put("Arquitectura", Arrays.asList(
                new Curso("Dibujo Técnico", "565"),
                new Curso("Diseño de Interiores", "687"),
                new Curso("Historia de la Arquitectura", "408")
        ));
        cursosPorCarrera.put("Economía", Arrays.asList(
                new Curso("Microeconomía", "578"),
                new Curso("Macroeconomía", "248"),
                new Curso("Econometría", "554")
        ));
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("----- Menú Principal -----");
            System.out.println("1. Registrar Alumno");
            System.out.println("2. Registrar Catedrático");
            System.out.println("3. Inscribir Alumno en Curso");
            System.out.println("4. Asignar Curso a Catedrático");
            System.out.println("5. Mostrar Alumnos y Catedraticos");
            System.out.println("6. Exportar Detalles");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarAlumno(scanner);
                    break;
                case 2:
                    registrarCatedratico(scanner);
                    break;
                case 3:
                    inscribirAlumnoEnCurso(scanner);
                    break;
                case 4:
                    asignarCursoACatedratico(scanner);
                    break;
                case 5:
                    mostrarAlumnosyCatedraticos();
                    break;
                case 6:
                    exportarDetalles();
                    break;
                case 7:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    private void registrarAlumno(Scanner scanner) {
        System.out.print("Nombre del Alumno: ");
        String nombre = scanner.nextLine();
        System.out.print("ID del Alumno: ");
        String id = scanner.nextLine();
        System.out.println("Selecciona la Carrera del Alumno:");
        String carrera = seleccionarCarrera(scanner);

        Alumno alumno = new Alumno(nombre, id, carrera);
        alumnos.add(alumno);
        System.out.println("Alumno registrado exitosamente.");
    }

    private void registrarCatedratico(Scanner scanner) {
        System.out.print("Nombre del Catedrático: ");
        String nombre = scanner.nextLine();
        System.out.print("ID del Catedrático: ");
        String id = scanner.nextLine();
        System.out.println("Selecciona la Carrera del Catedrático:");
        String profesion = seleccionarCarrera(scanner);

        Catedratico catedratico = new Catedratico(nombre, id, profesion);
        catedraticos.add(catedratico);
        System.out.println("Catedrático registrado exitosamente.");
    }

    //Metodo en el cual se seleccionan los cursos por medio de la lista
    private String seleccionarCarrera(Scanner scanner) {
        List<String> carreras = new ArrayList<>(cursosPorCarrera.keySet());
        for (int i = 0; i < carreras.size(); i++) {
            System.out.println((i + 1) + ". " + carreras.get(i));
        }
        String carreraSeleccionada = null;
        while (carreraSeleccionada == null) {
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            if (opcion > 0 && opcion <= carreras.size()) {
                carreraSeleccionada = carreras.get(opcion - 1);
            } else {
                System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
        return carreraSeleccionada;
    }

    private void seleccionarCursos(Scanner scanner, Persona persona) {
        List<Curso> cursosDisponibles = cursosPorCarrera.get(persona instanceof Alumno ? ((Alumno) persona).getCarrera() : ((Catedratico) persona).getProfesion());
        if (cursosDisponibles != null) {
            System.out.println("Selecciona los cursos disponibles:");
            for (int i = 0; i < cursosDisponibles.size(); i++) {
                System.out.println((i + 1) + ". " + cursosDisponibles.get(i).getNombre() + " Codigo: " + cursosDisponibles.get(i).getCodigo());
            }
            while (true) {
                System.out.print("Selecciona otro curso o presiona (0 para finalizar): ");
                int opcionCurso = scanner.nextInt();
                scanner.nextLine();
                if (opcionCurso == 0) break;
                if (opcionCurso > 0 && opcionCurso <= cursosDisponibles.size()) {
                    Curso curso = cursosDisponibles.get(opcionCurso - 1);
                    if (persona instanceof Alumno) {
                        ((Alumno) persona).inscribirCurso(curso);
                    } else if (persona instanceof Catedratico) {
                        ((Catedratico) persona).asignarCurso(curso);
                    }
                } else {
                    System.out.println("Opción no válida. Intenta de nuevo.");
                }
            }
        }
        System.out.println("Cursos asignados correctamente");
    }

    private void inscribirAlumnoEnCurso(Scanner scanner) {
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.\n");
            return;
        }
        System.out.println("Lista de Alumnos:");
        for (int i = 0; i < alumnos.size(); i++) {
            System.out.println((i + 1) + ". " + alumnos.get(i).getNombre());
        }
        Alumno alumnoSeleccionado = null;
        while (alumnoSeleccionado == null) {
            System.out.print("Elige el número del Alumno: ");
            int numAlumno = scanner.nextInt() - 1;
            scanner.nextLine();
            if (numAlumno >= 0 && numAlumno < alumnos.size()) {
                alumnoSeleccionado = alumnos.get(numAlumno);
            } else {
                System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
        seleccionarCursos(scanner, alumnoSeleccionado);
    }

    private void asignarCursoACatedratico(Scanner scanner) {
        if (catedraticos.isEmpty()) {
            System.out.println("No hay catedráticos registrados.\n");
            return;
        }
        System.out.println("Lista de Catedráticos:");
        for (int i = 0; i < catedraticos.size(); i++) {
            System.out.println((i + 1) + ". " + catedraticos.get(i).getNombre());
        }
        Catedratico catedraticoSeleccionado = null;
        while (catedraticoSeleccionado == null) {
            System.out.print("Elige el número del Catedrático: ");
            int numCatedratico = scanner.nextInt() - 1;
            scanner.nextLine();
            if (numCatedratico >= 0 && numCatedratico < catedraticos.size()) {
                catedraticoSeleccionado = catedraticos.get(numCatedratico);
            } else {
                System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
        seleccionarCursos(scanner, catedraticoSeleccionado);
        System.out.println("Cursos asignados correctamente");
    }

    private void mostrarAlumnosyCatedraticos() {
        //Alumnos
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.\n");
        } else {
            System.out.println("----- Lista de Alumnos -----");
            for (Alumno alumno : alumnos) {
                alumno.mostrarDetalles();
            }
            System.out.println();
        }
        //Catedraticos
        if (catedraticos.isEmpty()) {
            System.out.println("No hay catedráticos registrados.\n");
        } else {
            System.out.println("----- Lista de Catedráticos -----");
            for (Catedratico catedratico : catedraticos) {
                catedratico.mostrarDetalles();
            }
            System.out.println();
        }
    }

    public void exportarDetalles() {
        String rutaEscritorio = System.getProperty("user.home") + "/Desktop/detalles.txt";
        File archivo = new File(rutaEscritorio);

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("----- Lista de Alumnos -----");
            if (alumnos.isEmpty()) {
                writer.println("No hay alumnos registrados.");
            } else {
                for (Alumno alumno : alumnos) {
                    writer.println("Nombre: " + alumno.getNombre());
                    writer.println("ID: " + alumno.getId());
                    writer.println("Carrera: " + alumno.getCarrera());
                    writer.println("Cursos inscritos:");
                    for (Curso curso : alumno.getCursos()) {
                        writer.println("  * " + curso.getNombre() + " (Código: " + curso.getCodigo() + ")");
                    }
                    writer.println();
                }
            }

            writer.println("----- Lista de Catedráticos -----");
            if (catedraticos.isEmpty()) {
                writer.println("No hay catedráticos registrados.");
            } else {
                for (Catedratico catedratico : catedraticos) {
                    writer.println("Nombre: " + catedratico.getNombre());
                    writer.println("ID: " + catedratico.getId());
                    writer.println("Profesión: " + catedratico.getProfesion());
                    writer.println("Cursos asignados:");
                    for (Curso curso : catedratico.getCursos()) {
                        writer.println("  * " + curso.getNombre() + " (Código: " + curso.getCodigo() + ")");
                    }
                    writer.println();
                }
            }

            System.out.println("Detalles exportados correctamente a " + rutaEscritorio);
        } catch (IOException e) {
            System.err.println("Error al exportar los detalles: " + e.getMessage());
        }
    }

}