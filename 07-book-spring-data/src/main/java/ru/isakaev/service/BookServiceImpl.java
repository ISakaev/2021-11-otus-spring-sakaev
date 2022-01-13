package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.BookRepository;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Comment;
import ru.isakaev.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findBooksWithLazyFields();
    }

    @Override
    public Book getBook(int id) {
        return bookRepository.findByIdWithLazyFields(id).orElse(null);
//        return bookRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Book saveBook(String title, String authorName, String genreName, List<String> commentText) {
        Book book = bookRepository.findByTitle(title);
        List<Comment> collect = commentText.stream().map(c -> new Comment(c)).collect(Collectors.toList());
        if (book != null && book.getAuthor().getName().equalsIgnoreCase(authorName)
                && book.getGenre().getName().equalsIgnoreCase(genreName)){
            List<Comment> bookComments = book.getComments();
            for (Comment comment: bookComments) {
                if(!bookComments.contains(comment)){
                    book.getComments().add(comment);
                }
            }
            return book;
        }
        Book newBook = new Book(title, new Author(authorName), new Genre(genreName), collect);
        return bookRepository.save(newBook);
    }

    @Override
    @Transactional
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}
