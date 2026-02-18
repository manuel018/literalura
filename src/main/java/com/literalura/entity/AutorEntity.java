package com.literalura.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Autor")
public class AutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer birthYear;
    private Integer deathYear;
    private String name;


    public AutorEntity() {
    }

    public AutorEntity(Integer id, Integer birthYear, Integer deathYear, String name) {
        this.id = id;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
