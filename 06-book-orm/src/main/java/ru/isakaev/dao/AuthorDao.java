package ru.isakaev.dao;

import ru.isakaev.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    List<Author> getAll();

    Optional<Author> getById(Long id);

    Author save(Author author);

    void deleteById(Long id);

    List<Author> findByName(String name);
}
