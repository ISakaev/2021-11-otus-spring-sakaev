package ru.isakaev.service;

import ru.isakaev.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author getAuthor(Long id);

    Author saveAuthor(String name);

    void deleteAuthor(Long id);
}
