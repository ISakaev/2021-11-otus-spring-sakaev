package ru.isakaev.service;

import ru.isakaev.config.exception.MyException;
import ru.isakaev.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book getBook(Long id) throws MyException;

    Book saveBook(Book book);

    Book updateBook(Long id, Book newBook) throws MyException;

    void deleteBook(long id) throws MyException;

}