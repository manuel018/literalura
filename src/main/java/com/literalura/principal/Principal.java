package com.literalura.principal;

import com.literalura.model.Autor;
import com.literalura.model.Libro;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LanguageRepository;
import com.literalura.repository.LibroRepository;
import com.literalura.service.LibroService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    LibroRepository libroRepository;
    LibroService libroService;
    AutorRepository autorRepository;
    LanguageRepository languageRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository, LanguageRepository languageRepository) {
        this.libroRepository = libroRepository;
        this.libroService = new LibroService(libroRepository, autorRepository, languageRepository);
        this.languageRepository = languageRepository;
        this.autorRepository = autorRepository;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n--- Menú Literalura ---");
            System.out.println("1. Búsqueda de libro por título");
            System.out.println("2. Lista de todos los libros");
            System.out.println("3. Lista de autores");
            System.out.println("4. Listar autores vivos en determinado año");
            System.out.println("5. Exhibir cantidad de libros en un determinado idioma");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                opcion = -1;
            }
            switch (opcion) {
                case 1: {
                    System.out.print("Título del libro: ");
                    String titulo = scanner.nextLine();
                    List<Libro> libros = libroService.buscarLibro(titulo);
                    if (libros != null) {
                        System.out.println("Resultados de busqueda de libros: ");
                        libros.forEach(System.out::println);
                    } else {
                        System.out.println("No se encontró ningun libro con el titulo: " + titulo);
                    }
                    break;
                }
                case 2: {
                    List<Libro> libros = libroService.listarLibros();
                    if (libros == null) {
                        System.out.println("No hay libros guardados en la base de datos.");
                        break;
                    }
                    libros.forEach((libro) -> {
                        System.out.println("ID: " + libro.id());
                        System.out.println("Título: " + libro.title());
                        System.out.println("Autores: " + libro.authors().stream().map(Autor::name).toList());
                        System.out.println("Idiomas: " + libro.languages());
                        System.out.println("Resumen: " + (libro.summaries().isEmpty() ? "No hay resumen disponible." : libro.summaries().get(0)));
                        System.out.println("---------------------------------------------------");
                    });
                    break;
                }
                case 3: {
                    List<Autor> autores = libroService.listarAutores();
                    if (autores == null) {
                        System.out.println("No hay autores guardados en la base de datos.");
                        break;
                    }
                    autores.forEach((autor) -> {
                        System.out.println("Nombre: " + autor.name());
                        System.out.println("Año de nacimiento: " + autor.birthYear());
                        System.out.println("Año de fallecimiento: " + autor.deathYear());
                        System.out.println("---------------------------------------------------");
                    });
                    break;
                }
                case 4: {
                    System.out.print("Ingrese el año: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    List<Autor> autores = this.libroService.listarAutoresVivosEn(year);

                    if (autores == null) {
                        System.out.println("No hay autores para la fecha de: " + year);
                        break;
                    }

                    autores.forEach((autor) -> {
                        System.out.println("Nombre: " + autor.name());
                        System.out.println("Año de nacimiento: " + autor.birthYear());
                        System.out.println("Año de fallecimiento: " + (autor.deathYear() == null ? "Desconocido" : autor.deathYear()));
                        System.out.println("---------------------------------------------------");
                    });
                    break;
                }
                case 5: {
                    System.out.println("Seleccione idioma:");
                    System.out.println("1. Inglés (en)");
                    System.out.println("2. Español (es)");
                    try {
                        int idiomaOpcion = scanner.nextInt();
                        scanner.nextLine();
                        String idioma = idiomaOpcion == 1 ? "en" : "es";
                        libroService.contarLibrosPorIdioma(idioma);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida. Debe ingresar un número (1 o 2).");
                        scanner.nextLine();
                        break;
                    }
                }
                case 0: {
                    System.out.println("¡Hasta luego!");
                    break;
                }
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
}
