package ru.isakaev.service;

import ru.isakaev.config.exception.MyException;
import ru.isakaev.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    List<Author> findByNameContainText(String text);

    Author getAuthor(Long id) throws MyException;

    Author saveAuthor(Author author) throws MyException;

    void deleteAuthor(Long id) throws MyException;

    Author updateAuthor(Long id, Author author) throws MyException;
}
