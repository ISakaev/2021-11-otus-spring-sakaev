package ru.isakaev.service;

import ru.isakaev.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();

    Genre getGenre(long id);

    Genre saveGenre(Genre genre);

    void deleteGenre(long id);
}
