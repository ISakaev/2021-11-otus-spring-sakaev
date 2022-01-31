package ru.isakaev.dao;

import ru.isakaev.model.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getAll();

    Genre getById(int id);

    Genre save(Genre genre);

    void deleteById(int id);
}
