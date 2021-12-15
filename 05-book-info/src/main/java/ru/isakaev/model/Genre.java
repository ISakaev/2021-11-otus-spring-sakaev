package ru.isakaev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Genre {

    private Integer id;
    private String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this.name = name;
    }
}
