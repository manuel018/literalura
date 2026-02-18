# 📚 Literalura - Reto Alura

Aplicación de consola desarrollada en Java como parte del **curso de Alura**, que permite gestionar libros y autores obtenidos desde una API externa y almacenados en una base de datos relacional.

---

## 🚀 Funcionalidades

El programa presenta un menú interactivo en consola:

--- 
### Menú Literalura
    Búsqueda de libro por título

    Lista de todos los libros

    Lista de autores

    Listar autores vivos en determinado año

    Exhibir cantidad de libros en un determinado idioma

    Salir

### Detalle de opciones
1. **Búsqueda de libro por título**
    - Consulta primero en la base de datos.
    - Si no existe, busca en la API, guarda los resultados y los muestra.

2. **Lista de todos los libros**
    - Muestra todos los libros guardados en la base de datos con sus autores, idiomas y resumen.

3. **Lista de autores**
    - Muestra todos los autores registrados en la base de datos.

4. **Listar autores vivos en determinado año**
    - Filtra autores cuya fecha de muerte es posterior al año ingresado (o nula).

5. **Exhibir cantidad de libros en un determinado idioma**
    - Permite seleccionar idioma (ejemplo: inglés o español).
    - Muestra cuántos libros están registrados en ese idioma.

0. **Salir**
    - Finaliza la ejecución del programa.

---

## 🛠️ Tecnologías utilizadas
- **Java 17+**
- **Spring Boot** (para repositorios y servicios)
- **JPA/Hibernate** (para persistencia)
- **Base de datos relacional** (PostgreSQL)
- **API externa de libros** (para obtener datos iniciales)

---

## 📂 Estructura principal
- `LibroEntity` → Entidad que representa un libro.
- `AutorEntity` → Entidad que representa un autor.
- `Language` → Entidad que representa un idioma.
- `LibroService` → Lógica de negocio (buscar, listar, contar).
- `AutorRepository`, `LibroRepository`, `LanguageRepository` → Interfaces JPA para acceso a datos.

---
