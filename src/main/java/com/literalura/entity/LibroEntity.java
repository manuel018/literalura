package com.literalura.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Libro")
public class LibroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int idLibro;

    private String title;

    @Column(columnDefinition = "text")
    private String summaries;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = {@JoinColumn(name = "id_libro")}, inverseJoinColumns = {@JoinColumn(name = "id_language")})
    private Set<Language> languages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = {@JoinColumn(name = "id_libro")}, inverseJoinColumns = {@JoinColumn(name = "id_autor")})
    private Set<AutorEntity> autores;

    public LibroEntity(Integer id, int idLibro, String title, String summaries, Set<Language> languages, Set<AutorEntity> autores) {
        this.id = id;
        this.idLibro = idLibro;
        this.title = title;
        this.summaries = summaries;
        this.languages = languages;
        this.autores = autores;
    }

    public LibroEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummaries() {
        return summaries;
    }

    public void setSummaries(String summaries) {
        this.summaries = summaries;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Set<AutorEntity> getAutores() {
        return autores;
    }

    public void setAutores(Set<AutorEntity> autores) {
        this.autores = autores;
    }
}
