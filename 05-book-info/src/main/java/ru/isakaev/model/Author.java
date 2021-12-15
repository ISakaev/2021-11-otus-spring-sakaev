package ru.isakaev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
//@AllArgsConstructor
public class Author {

    private Integer id;
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public Author(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
