package ru.isakaev.dao;

import ru.isakaev.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    List<Genre> getAll();

    Optional<Genre> getById(int id);

    List<Genre> findByName(String name);

    Genre save(Genre genre);

    void deleteById(int id);
}
