package ru.isakaev.service;

import ru.isakaev.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    List<Author> findByNameContainText(String text);

    Author getAuthor(Long id);

    Author saveAuthor(Author author);

    void deleteAuthor(Long id);

}
