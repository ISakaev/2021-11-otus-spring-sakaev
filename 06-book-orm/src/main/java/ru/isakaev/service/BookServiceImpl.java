package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.BookDao;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;
import ru.isakaev.model.dto.BookDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<BookDto> getAll() {
        List<Book> all = bookDao.getAll();
        return all.stream().map(book -> new BookDto(book.getId(), book.getTitle())).collect(Collectors.toList());

    }

    @Override
    public Book getBook(Long id) {
        return bookDao.getById(id).orElse(null);
    }

    @Override
    @Transactional
    public Book saveBook(String title, String authorName, String genreName) {
        List<Book> books = bookDao.findByName(title);
        // List<Comment> не учитываем при сравнении объектов
        if (!books.isEmpty() && books.get(0).getAuthor().getName().equalsIgnoreCase(authorName)
                && books.get(0).getGenre().getName().equalsIgnoreCase(genreName)){
            Book book = books.get(0);
            return book;
        }
        Book newBook = new Book(title, new Author(authorName), new Genre(genreName));
        return bookDao.save(newBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookDao.deleteById(id);
    }
}
