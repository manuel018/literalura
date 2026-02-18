package com.literalura;

import com.literalura.principal.Principal;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LanguageRepository;
import com.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    LibroRepository libroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LanguageRepository languageRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Principal principal = new Principal(libroRepository,autorRepository,languageRepository);
        principal.showMenu();
    }
}