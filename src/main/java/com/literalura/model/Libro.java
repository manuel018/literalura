package com.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Libro(
        @JsonProperty("id")
        int id,

        @JsonProperty("title")
        String title,

        @JsonProperty("authors")
        List<Autor> authors,

        @JsonProperty("summaries")
        List<String> summaries,

        @JsonProperty("languages")
        List<String> languages
) {
}
