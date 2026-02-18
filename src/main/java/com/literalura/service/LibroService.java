package com.literalura.service;

import com.literalura.entity.AutorEntity;
import com.literalura.entity.Language;
import com.literalura.entity.LibroEntity;
import com.literalura.model.Autor;
import com.literalura.model.Libro;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LanguageRepository;
import com.literalura.repository.LibroRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LibroService {

    ApiService apiService = new ApiService();

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private LanguageRepository languageRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository, LanguageRepository languageRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.languageRepository = languageRepository;
    }

    public List<Autor> listarAutores() {
        try {
            List<AutorEntity> result = this.autorRepository.findAll();

            List<Autor> response = result.stream().map((autorEntity) ->
                    new Autor(
                            autorEntity.getBirthYear(),
                            autorEntity.getDeathYear(),
                            autorEntity.getName()
                    )
            ).toList();

            return response;
        } catch (Error e) {
            System.err.println("Error global en listarAutores: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public List<Libro> buscarLibro(String titulo) {
        try {
            // Buscar en BD
            List<LibroEntity> result = this.libroRepository.findByTitleContainingIgnoreCase(titulo);

            List<Libro> response = result.stream().map((libroEntity) -> {
                List<Autor> libroAutores = libroEntity.getAutores().stream()
                        .map((autor) -> new Autor(autor.getBirthYear(), autor.getDeathYear(), autor.getName()))
                        .toList();

                List<String> libroSummaries = Arrays.asList(libroEntity.getSummaries());
                List<String> libroLanguages = libroEntity.getLanguages().stream()
                        .map(Language::getLanguage)
                        .toList();

                return new Libro(libroEntity.getIdLibro(), libroEntity.getTitle(), libroAutores, libroSummaries, libroLanguages);
            }).toList();

            // Si no hay en BD, buscar en API
            if (response.isEmpty()) {
                List<Libro> apiResponse = this.apiService.buscarLibro(titulo);
                if (apiResponse.isEmpty()) return null;

                apiResponse.forEach((libro) -> {
                    // Validar autores
                    Set<AutorEntity> autores = libro.authors().stream()
                            .map((autor) -> autorRepository.findByNameIgnoreCase(autor.name())
                                    .orElseGet(() -> {
                                        AutorEntity nuevoAutor = new AutorEntity(null, autor.birthYear(), autor.deathYear(), autor.name());
                                        return autorRepository.save(nuevoAutor);
                                    })
                            ).collect(Collectors.toSet());

                    // Validar idiomas
                    Set<Language> languages = libro.languages().stream()
                            .map((langStr) -> languageRepository.findByLanguageIgnoreCase(langStr)
                                    .orElseGet(() -> {
                                        Language nuevoLang = new Language();
                                        nuevoLang.setLanguage(langStr);
                                        return languageRepository.save(nuevoLang);
                                    })
                            ).collect(Collectors.toSet());

                    // Crear libro
                    LibroEntity libroEntity = new LibroEntity(
                            null,
                            libro.id(),
                            libro.title(),
                            libro.summaries().isEmpty() ? "No hay un resumen disponible." : libro.summaries().get(0),
                            languages,
                            autores
                    );

                    this.libroRepository.save(libroEntity);
                });

                return apiResponse;
            } else {
                return response;
            }
        } catch (Error e) {
            System.err.println("Error global en buscarLibro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public List<Autor> listarAutoresVivosEn(int year) {
        try {
            List<AutorEntity> result = this.autorRepository.findAll();

            List<Autor> response = result.stream()
                    .filter((autorEntity) -> autorEntity.getDeathYear() == null || autorEntity.getDeathYear() > year)
                    .map((autorEntity) -> new Autor(
                            autorEntity.getBirthYear(),
                            autorEntity.getDeathYear(),
                            autorEntity.getName()
                    ))
                    .toList();

            if (response.isEmpty()) {
                System.out.println("No hay autores vivos en el año " + year);
                return null;
            }

            return response;
        } catch (Error e) {
            System.err.println("Error global en listarAutoresVivosEn: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public List<Libro> listarLibros() {
        try {
            List<LibroEntity> result = this.libroRepository.findAll();

            List<Libro> response = result.stream().map((libroEntity) -> {
                List<Autor> libroAutores = libroEntity.getAutores().stream()
                        .map((autor) -> new Autor(autor.getBirthYear(), autor.getDeathYear(), autor.getName()))
                        .toList();

                List<String> libroSummaries = Arrays.asList(libroEntity.getSummaries());
                List<String> libroLanguages = libroEntity.getLanguages().stream()
                        .map(Language::getLanguage)
                        .toList();

                return new Libro(libroEntity.getIdLibro(), libroEntity.getTitle(), libroAutores, libroSummaries, libroLanguages);
            }).toList();

            return response;
        } catch (Error e) {
            System.err.println("Error global en listarLibros: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public void contarLibrosPorIdioma(String idioma) {
        try {
            List<LibroEntity> libros = libroRepository.findAll();
            long cantidad = libros.stream().filter(libro -> libro.getLanguages().stream().anyMatch(lang -> lang.getLanguage().equalsIgnoreCase(idioma))).count();
            if (cantidad == 0) {
                System.out.println("No hay libros registrados en el idioma: " + idioma);
            } else {
                System.out.println("Idioma: " + idioma + " | Cantidad de libros: " + cantidad);
            }
        } catch (Error e) {
            System.err.println("Error global en contarLibrosPorIdioma: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
