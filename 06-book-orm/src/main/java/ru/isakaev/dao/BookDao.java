package ru.isakaev.dao;

import ru.isakaev.model.Book;

import java.util.List;

public interface BookDao {

    List<Book> getAll();

    Book getById(Long id);

    List<Book> findByName(String title);

    Book save(Book book);

    void deleteById(Long id);

}
