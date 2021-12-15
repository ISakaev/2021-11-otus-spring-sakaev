package ru.isakaev.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class Book {

    private Integer id;
    private String name;
    private Author author;
    private Genre genre;

    public Book(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Book() {
    }

    public Book(Integer id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}
