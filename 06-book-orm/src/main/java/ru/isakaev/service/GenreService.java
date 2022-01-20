package ru.isakaev.service;

import ru.isakaev.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre getGenre(Long id);

    Genre saveGenre(String name);

    void deleteGenre(Long id);
}
