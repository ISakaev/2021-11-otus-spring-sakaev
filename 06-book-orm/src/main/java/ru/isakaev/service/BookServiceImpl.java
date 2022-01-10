package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.BookDao;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Comment;
import ru.isakaev.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBook(int id) {
        return bookDao.getById(id).orElse(null);
    }

    @Override
    @Transactional
    public Book saveBook(String title, String authorName, String genreName, List<String> commentText) {
        List<Book> books = bookDao.findByName(title);
        List<Comment> collect = commentText.stream().map(c -> new Comment(c)).collect(Collectors.toList());
        // List<Comment> не учитываем при сравнении объектов
        if (!books.isEmpty() && books.get(0).getAuthor().getName().equalsIgnoreCase(authorName)
                && books.get(0).getGenre().getName().equalsIgnoreCase(genreName)){
            Book book = books.get(0);
            List<Comment> bookComments = book.getComments();
            for (Comment comment: bookComments) {      // используем цикл для правильной последовательности создания коментариев в тестах
                if(!bookComments.contains(comment)){
                    book.getComments().add(comment);
                }
            }
            return book;
        }
        Book newBook = new Book(title, new Author(authorName), new Genre(genreName), collect);
        return bookDao.save(newBook);
    }

    @Override
    @Transactional
    public void deleteBook(int id) {
        bookDao.deleteById(id);
    }
}
