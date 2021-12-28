package ru.isakaev.dao;

import ru.isakaev.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    List<Book> getAll();

    Optional<Book> getById(int id);

    Book findByName(String title);

    Book save(Book book);

    void deleteById(int id);

}
