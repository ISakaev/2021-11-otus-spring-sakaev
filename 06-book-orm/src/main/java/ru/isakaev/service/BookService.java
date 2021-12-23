package ru.isakaev.service;

import ru.isakaev.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book getBook(int id);

    Book saveBook(String title, String AuthorName, String genreName);

    void deleteBook(int id);
}
