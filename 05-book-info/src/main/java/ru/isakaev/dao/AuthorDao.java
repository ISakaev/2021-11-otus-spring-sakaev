package ru.isakaev.dao;

import ru.isakaev.model.Author;
import ru.isakaev.model.Book;

import java.util.List;

public interface AuthorDao {

    List<Author> getAll();

    Author getById(int id);

    Author save(Author author);

    void deleteById(int id);
}
