package ru.isakaev.service;

import ru.isakaev.model.Book;
import ru.isakaev.model.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAll();

    Book getBook(Long id);

    Book saveBook(String title, String AuthorName, String genreName);

    void deleteBook(Long id);
}
