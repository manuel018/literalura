package com.literalura.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.model.ApiResponse;
import com.literalura.model.Autor;
import com.literalura.model.Libro;

public class ApiService {

    private static final String BASE_URL = "https://gutendex.com/books/?";
    private HttpClient cliente = HttpClient.newHttpClient();
    private ObjectMapper mapper = new ObjectMapper();


    public List<Libro> buscarLibro(String titulo) {
        try {
            String urlStr = BASE_URL.concat("search=").concat(titulo.replace(" ", "%20"));
            System.out.println("Se consulta la api: " + urlStr);
            HttpRequest solicitud = HttpRequest.newBuilder().uri(URI.create(urlStr)).GET().build();
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            String json = respuesta.body();
            System.out.println("El body es: " + json);
            ApiResponse apiResponse = mapper.readValue(json, ApiResponse.class);
            if (apiResponse.results() != null && !apiResponse.results().isEmpty()) {
                return apiResponse.results();
            } else {
                System.out.println("No se encontraron resultados.");
                return List.of();
            }
        } catch (Exception e) {
            System.err.println("Error: ".concat(e.getMessage()));
            return List.of();
        }
    }

    public List<Autor> getAutores() {
        return null;
    }
}
