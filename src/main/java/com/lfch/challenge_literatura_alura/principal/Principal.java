package com.lfch.challenge_literatura_alura.principal;


import com.lfch.challenge_literatura_alura.model.Autor;
import com.lfch.challenge_literatura_alura.model.DatosLibro;
import com.lfch.challenge_literatura_alura.model.DatosLibros;
import com.lfch.challenge_literatura_alura.model.Libro;
import com.lfch.challenge_literatura_alura.repository.AutorRepository;
import com.lfch.challenge_literatura_alura.repository.LibroRepository;
import com.lfch.challenge_literatura_alura.service.ConsumoAPI;
import com.lfch.challenge_literatura_alura.service.ConvertirDatos;
import com.lfch.challenge_literatura_alura.utils.IngresarDatos;
import com.lfch.challenge_literatura_alura.view.ComunicacionAppUsuario;
import jakarta.transaction.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    final String URL_BASE = "https://gutendex.com/books/?search=%20";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvertirDatos convertir = new ConvertirDatos();
    private ComunicacionAppUsuario comunicacion = new ComunicacionAppUsuario();
    private IngresarDatos digitar = new IngresarDatos();
    private LibroRepository repositorio;
    private AutorRepository repositorioAutor;
    String nombreLibro;
    ArrayList<Integer> validOptions = new ArrayList<>
            (Arrays.asList(0,1, 2, 3, 4, 5, 6,7,8,9,10,11));
    private Optional<Libro> libroBuscada;
    private List<String> idiomas;
    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.repositorioAutor =autorRepository;
    }
    public void muestraElMenu() {
        comunicacion.saludo();
        while (true) {

            comunicacion.menuPrincipal();
            var opcion = digitar.ingresarNumeroEntero();
            digitar.limpiarLinea();
            if (validOptions.contains(opcion)) {
                if (opcion == 0) {
                    comunicacion.despedida();
                    break;
                }
                switch (opcion){
                    case 1:
                        buscarLibroApi();
                        combinarAutoresDuplicados();
                        break;
                    case 2:
                        buscarPorIdioma();
                        break;
                    case 3:
                        buscarPorAutor();
                        break;
                    case 4:
                        buscarPorRangoDescargas();
                        break;
                    case 5:
                        buscarPorFechaNacimiento();
                        break;
                    case 6:
                        buscarPorFechaMuerte();
                        break;
                    case 7:
                        buscarPorEdad();
                        break;
                    case 8:
                        buscarPorRangoEdad();
                        break;
                    case 9:
                        mostrarListadoTotalLiros();
                        break;
                    case 10:
                        estadisticasAutores();
                        break;
                    case 11:
                        estadisticasLibros();
                        break;
                }

            }else {
                System.err.println("Valor digitado no valido");
            }
        }

    }

    public void buscarLibroApi() {
        // Solicitar el nombre del libro
        comunicacion.solicitarNombreLibro();
        nombreLibro = digitar.ingresarTexto();
        String nombre = nombreLibro.replace(" ", "+");

        // Obtener datos desde la API
        String json = consumoApi.obtenerDatos(URL_BASE + nombre);
        var librosTotal = convertir.obtenerListaDatos(json, DatosLibros.class);

        // Buscar el libro en la API
        Libro libro = librosTotal.libros().stream()
                .filter(s -> s.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst()
                .map(Libro::new)
                .orElse(null);

        if (libro == null) {
            System.out.println("No se encontró el libro.");
            return;
        }

        // Verificar si el libro ya existe en la base de datos
        Optional<Libro> libroExistente = repositorio.findByTituloContainsIgnoreCase(libro.getTitulo());
        if (libroExistente.isPresent()) {
            System.out.println("El libro ya existe en la base de datos: " + libroExistente.get().getTitulo());
            return;
        }
        // Guardar el libro en la base de datos
        repositorio.save(libro);
        System.out.println("El libro '%s' fue guardado exitosamente en la base de datos.".formatted(nombreLibro));
    }



    private void buscarPorIdioma() {
        List<Libro> libros = repositorio.busquedaSQLNativo();
        idiomas = libros.stream()
                .sorted(Comparator.comparing(Libro::getLenguaje))
                .map(Libro::getLenguaje)
                .distinct()
                .collect(Collectors.toList());
        for (int i = 0; i < idiomas.size(); i++) {
            System.out.println(i+1 +" Opción idioma: " + idiomas.get(i));
        }
        System.out.println("Por favor ingresa el número que corresponde al idioma seleccionado: ");
        int opcionIdioma =digitar.ingresarNumeroEntero();
        digitar.limpiarLinea();
        String idiomaSeleccionado = idiomas.get(opcionIdioma-1);
        List<Libro> librosFiltrados = libros.stream()
                .filter(libro -> libro.getLenguaje().equals(idiomaSeleccionado))
                .collect(Collectors.toList());
        librosFiltrados.forEach(s-> System.out.println(
                "Titulo: " + s.getTitulo() +
                        "\n Autor: " + s.getAutor() +
                        "\n Numero de descargas: " + s.getNumeroDescargas()
        ));
        Long totalLibrosFiltrados = libros.stream()
                .filter(libro -> libro.getLenguaje().equals(idiomaSeleccionado))
                .count();
        System.out.println("Total libros disponibles en este idioma: " + totalLibrosFiltrados);
    }
    private void buscarPorAutor(){
        List<Libro> libros = repositorio.busquedaSQLNativo();
        System.out.println("Indique el nombre del Autor a Buscar: ");
        String autorBuscar = digitar.ingresarTexto();
        List<Libro> librosFiltrados = libros.stream()
                .filter(libro -> libro.getAutor().toString().toLowerCase().contains(autorBuscar.toLowerCase()))
                .collect(Collectors.toList());
        librosFiltrados.forEach(s-> System.out.println(
                "Titulo: " + s.getTitulo() +
                        "\n Autor: " + s.getAutor() +
                        "\n Numero de descargas: " + s.getNumeroDescargas()
        ));
        if (librosFiltrados.isEmpty()) {
            System.out.println("No se encontro el autor indicado");
        }
    }
    private void buscarPorRangoDescargas(){
        List<Libro> libros = repositorio.busquedaSQLNativo();
        System.out.println("Indique el rango de número de descargas ");
        System.out.println("Numero de descargas maximo; ");
        int maxDescargas = digitar.ingresarNumeroEntero();
        digitar.limpiarLinea();
        System.out.println("Numero de descargas minimo; ");
        int minDescargas = digitar.ingresarNumeroEntero();
        digitar.limpiarLinea();
        List<Libro> librosFiltrados = libros.stream()
                .filter(libro ->libro.getNumeroDescargas() <= maxDescargas &&libro.getNumeroDescargas() >= minDescargas)
                .sorted(Comparator.comparing(Libro::getNumeroDescargas).reversed())
                .collect(Collectors.toList());
        librosFiltrados.forEach(s-> System.out.println(
                "Titulo: " + s.getTitulo() +
                        "\n Autor: " + s.getAutor() +
                        "\n Numero de descargas: " + s.getNumeroDescargas()
        ));
        if (librosFiltrados.isEmpty()) {
            System.out.println("No se encontraron libros en ese rango de número de descargas");
        }
    }
    private void buscarPorFechaNacimiento(){
        System.out.println("indica por favor el intervalo de fecha de nacimiento para la busqueda");
        System.out.println("Fecha de nacimiento maxima: ");
        Integer fechaNacMaxima = digitar.ingresarNumeroEntero();
        System.out.println("Fecha de nacimiento minima: ");
        Integer fechaNacMinima = digitar.ingresarNumeroEntero();
        digitar.limpiarLinea();
        List<Autor> autores = repositorioAutor.busquedaJPQL(fechaNacMaxima,fechaNacMinima);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron Autores en ese rango");
            return;
        }
        autores.stream().distinct()
                .forEach(a -> {
                    System.out.println("Autor: " + a.getNombre() +
                            "\nFecha de Nacimiento: " + a.getFechaNacimiento() +
                            "\nFecha de Fallecimiento: " + a.getFechaMuerte() +
                            "\nEdad: " + a.getEdad() +
                            "\nLibros: ");
                    a.getLibro().forEach(l -> System.out.println("- " + l.getTitulo()));
                });
        int numeroLibros = repositorioAutor.cuentaLibrosPorFechas(fechaNacMaxima,fechaNacMinima);
        System.out.println("Número total de libros escritos " +
                "por autores nacidos en el rango indicado" + numeroLibros);
    }
    @Transactional
    public void combinarAutoresDuplicados() {
        // Paso 1: Identificar autores duplicados (basado en el nombre)
        List<Autor> autores = repositorioAutor.findAll();
        Map<String, List<Autor>> autoresDuplicados = autores.stream()
                .collect(Collectors.groupingBy(Autor::getNombre))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1) // Filtrar duplicados
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<String, List<Autor>> entry : autoresDuplicados.entrySet()) {
            List<Autor> duplicados = entry.getValue();
            Autor autorPrincipal = duplicados.get(0); // Seleccionar el primer autor como el principal

            // Paso 2: Actualizar los libros para que apunten al autor principal
            for (int i = 1; i < duplicados.size(); i++) { // Omitir el autor principal
                Autor autorDuplicado = duplicados.get(i);

                // Obtener todos los libros relacionados con el autor duplicado
                List<Libro> librosDelDuplicado = autorDuplicado.getLibro();
                for (Libro libro : librosDelDuplicado) {
                    // Eliminar el autor duplicado del libro
                    libro.getAutor().remove(autorDuplicado);

                    // Agregar el autor principal al libro si no está ya asociado
                    if (!libro.getAutor().contains(autorPrincipal)) {
                        libro.getAutor().add(autorPrincipal);
                    }

                    repositorio.save(libro); // Guardar el libro actualizado
                }

                // Paso 3: Eliminar el autor duplicado de la base de datos
                autorDuplicado.setLibro(null); // Desasociar los libros del autor duplicado
                repositorioAutor.delete(autorDuplicado); // Eliminar el autor duplicado
            }
        }
    }
    public void buscarPorFechaMuerte(){
        System.out.println("indica por favor el intervalo de fecha de fallecimiento para la busqueda");
        System.out.println("Fecha de fallecimiento maxima: ");
        Integer fechaFallMaxima= digitar.ingresarNumeroEntero();
        System.out.println("Fecha de fallecimiento minima: ");
        Integer fechaFallMaxMinima = digitar.ingresarNumeroEntero();
        digitar.limpiarLinea();
        List<Autor> autores = repositorioAutor.busquedaFechaMuerte(fechaFallMaxima,fechaFallMaxMinima);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron Autores en ese rango");
            return;
        }
        autores.stream()
                .forEach(a -> {
                    System.out.println("Autor: " + a.getNombre() +
                            "\nFecha de Nacimiento: " + a.getFechaNacimiento() +
                            "\nFecha de Fallecimiento: " + a.getFechaMuerte() +
                            "\nEdad: " + a.getEdad() +
                            "\nLibros: ");
                    a.getLibro().forEach(l -> System.out.println("- " + l.getTitulo()));
                });
    }
    private void buscarPorEdad(){
        System.out.println("indica por favor la edad por la que quieres realizar la Busqueda");
        Integer edadIndicada = digitar.ingresarNumeroEntero();
        digitar.limpiarLinea();
        List<Autor> autoresEdad = repositorioAutor.busquedaPorEdad(edadIndicada);
        if (autoresEdad.isEmpty()) {
            System.out.println("No se encontraron Autores en con esa edad");
            return;
        }
        autoresEdad.stream()
                .forEach(a -> {
                    System.out.println("Autor: " + a.getNombre() +
                            "\nFecha de Nacimiento: " + a.getFechaNacimiento() +
                            "\nFecha de Fallecimiento: " + a.getFechaMuerte() +
                            "\nEdad: " + a.getEdad() +
                            "\nLibros: ");
                    a.getLibro().forEach(l -> System.out.println("- " + l.getTitulo()));
                });
    }
    private void buscarPorRangoEdad(){
        System.out.println("indica por favor el rango de edad para la busqueda");
        System.out.println("Edad maxima: ");
        Integer edadMaxima= digitar.ingresarNumeroEntero();
        System.out.println("Edad minima: ");
        Integer edadMinima = digitar.ingresarNumeroEntero();
        digitar.limpiarLinea();
        List<Autor> autores = repositorioAutor.busquedaRangoEdad(edadMaxima,edadMinima);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron Autores en ese rango de edad");
            return;
        }
        autores.stream()
                .forEach(a -> {
                    System.out.println("Autor: " + a.getNombre() +
                            "\nFecha de Nacimiento: " + a.getFechaNacimiento() +
                            "\nFecha de Fallecimiento: " + a.getFechaMuerte() +
                            "\nEdad: " + a.getEdad() +
                            "\nLibros: ");
                    a.getLibro().forEach(l -> System.out.println("- " + l.getTitulo()));
                });
    }
    private void mostrarListadoTotalLiros(){
        List<Libro> libros = repositorio.findAll();
        libros.stream().sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(l -> System.out.println(
                        "Titulo: " + l.getTitulo() +
                                "\nAutor: " + l.getAutor().stream()
                                .map(Autor::getNombre) // Obtener solo los nombres
                                .collect(Collectors.joining(", ")) +
                                "\nNúmero de Descargas: "  + l.getNumeroDescargas()
                                + "\nIdioma: " + l.getLenguaje()
                ));
    }
    private void estadisticasAutores(){
        List<Autor> autores = repositorioAutor.findAll();
        long numeroAutores = autores.stream()
                .count();
        System.out.println("Número de Autores: " + numeroAutores);
        IntSummaryStatistics estadisticas = autores.stream()
                .mapToInt(Autor::getFechaNacimiento)
                .summaryStatistics();
        System.out.println("Fecha mas reciente de nacimiento: " + estadisticas.getMax());
        System.out.println("Fecha de nacimiento mas antigua: " + estadisticas.getMin());
        IntSummaryStatistics estadisticasMuerte = autores.stream()
                .mapToInt(Autor::getFechaMuerte)
                .summaryStatistics();
        System.out.println("Fecha mas reciente de muerte: " + estadisticasMuerte.getMax());
        System.out.println("Fecha de muerte mas antigua: " + estadisticasMuerte.getMin());
        IntSummaryStatistics estadisticasEdad = autores.stream()
                .mapToInt(Autor::getEdad)
                .summaryStatistics();
        System.out.println("Edad maxima: " + estadisticasEdad.getMax());
        System.out.println("Menor edad: " + estadisticasEdad.getMin());
        System.out.println("Promedio Edad: " + estadisticasEdad.getAverage());
    }
    private void estadisticasLibros(){
        List<Libro> libros = repositorio.findAll();
        IntSummaryStatistics estadisticasLibros = libros.stream()
                .mapToInt(Libro::getNumeroDescargas)
                .summaryStatistics();
        System.out.println("Numero total de libros: " + estadisticasLibros.getCount());
        System.out.println("Numero total de descargas: " + estadisticasLibros.getSum());
        System.out.println("Numero mayor de descargas de un libro: " + estadisticasLibros.getMax());
        System.out.println("Numero menor de descargas de un libro: " + estadisticasLibros.getMin());
        System.out.println("Numero promedio de descargas: " + estadisticasLibros.getAverage());
    }

}