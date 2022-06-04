package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.model.Book;
import ru.isakaev.repo.BookRepository;

import java.util.List;

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
    public Book getBook(long id) {
        return bookRepository.getById(id);
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
    public void deleteBook(long id) {
        Book book = bookRepository.getById(id);
        book.setAuthor(null);
        book.setGenre(null);
        bookRepository.delete(book);
    }
}
