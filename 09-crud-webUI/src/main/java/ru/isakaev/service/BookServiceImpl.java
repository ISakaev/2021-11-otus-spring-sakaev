package ru.isakaev.service;

import org.springframework.stereotype.Service;
import ru.isakaev.model.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public Book getBook(int id) {
        return null;
    }

    @Override
    public Book saveBook(String title, String AuthorName, String genreName, List<String> comments) {
        return null;
    }

    @Override
    public void deleteBook(int id) {

    }
}
