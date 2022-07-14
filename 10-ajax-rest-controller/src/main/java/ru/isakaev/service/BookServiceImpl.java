package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.config.exception.MyException;
import ru.isakaev.model.Book;
import ru.isakaev.repo.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(Long id) throws MyException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new MyException(String.format("Book with id=%s not found", id));
        }
        return optionalBook.get();
    }

    @Override
    @Transactional
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
    public Book updateBook(Long id, Book updateBook) throws MyException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()){
            throw new MyException(String.format("Book with id=%s not found", id));
        }
        Book book = optionalBook.get();
        book.setAuthor(updateBook.getAuthor());
        book.setGenre(updateBook.getGenre());
        book.setTitle(updateBook.getTitle());
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(long id) throws MyException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()){
            throw new MyException(String.format("Book with id=%s not found", id));
        }
        Book book = optionalBook.get();
        book.setAuthor(null);
        book.setGenre(null);
        bookRepository.delete(book);
    }
}