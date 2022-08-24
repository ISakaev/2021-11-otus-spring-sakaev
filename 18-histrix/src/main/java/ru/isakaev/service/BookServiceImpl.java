package ru.isakaev.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;
import ru.isakaev.repo.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultBooksFindAll")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultBook")
    public Book getBook(long id) {
        return bookRepository.getById(id);
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "defaultBookSave")
    public Book saveBook(Book newBook) {
        Book book = bookRepository.findByTitle(newBook.getTitle());
        if (book != null && book.getAuthor().getName().equalsIgnoreCase(newBook.getAuthor().getName())
                && book.getGenre().getName().equalsIgnoreCase(newBook.getGenre().getName())){

            return book;
        }
        return bookRepository.save(newBook);
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "defaultDeleteBook")
    public void deleteBook(long id) {
        Book book = bookRepository.getById(id);
        book.setAuthor(null);
        book.setGenre(null);
        bookRepository.delete(book);
    }

    public List<Book> defaultBooksFindAll() {
        return List.of(new Book(-1L,
                            "HystrixTitle",
                            new Author(-1L,"HystrixTitle"),
                            new Genre(-1L, "HystrixTitle")));
    }

    public Book defaultBook(long id) {
        return new Book(-1L,
                "HystrixTitle",
                new Author(-1L,"HystrixTitle"),
                new Genre(-1L, "HystrixTitle"));
    }

    public Book defaultBookSave(Book book) {
        return new Book(-1L,
                "HystrixTitle",
                new Author(-1L,"HystrixTitle"),
                new Genre(-1L, "HystrixTitle"));
    }

    public void defaultDeleteBook(long id) {

    }
}
