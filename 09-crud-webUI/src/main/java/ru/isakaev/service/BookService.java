package ru.isakaev.service;

import ru.isakaev.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book getBook(long id);

    Book saveBook(Book book);

    void deleteBook(long id);

}
