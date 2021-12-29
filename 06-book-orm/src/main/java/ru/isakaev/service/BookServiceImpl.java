package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.BookDao;
import ru.isakaev.model.Book;
import ru.isakaev.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    public BookServiceImpl(BookDao bookDao, AuthorService authorService, GenreService genreService, CommentService commentService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book getBook(int id) {
        return bookDao.getById(id).orElse(null);
    }

    @Override
    @Transactional
    public Book saveBook(String title, String authorName, String genreName, List<String> commentText) {
        List<Book> books = bookDao.findByName(title);
        List<Comment> collect = commentText.stream().map(c -> commentService.saveComment(c)).collect(Collectors.toList());
        // List<Comment> не учитываем при сравнении объектов
        if (!books.isEmpty() && books.get(0).getAuthor().getName().equalsIgnoreCase(authorName)
                && books.get(0).getGenre().getName().equalsIgnoreCase(genreName)){
            Book book = books.get(0);
            List<Comment> bookComments = book.getComments();
            collect.stream().filter(c -> !bookComments.contains(c)).forEach(com -> book.getComments().add(com));
            return book;
        }
        Book newBook = new Book(title, authorService.saveAuthor(authorName), genreService.saveGenre(genreName), collect);
        return bookDao.save(newBook);
    }

    @Override
    @Transactional
    public void deleteBook(int id) {
        bookDao.deleteById(id);
    }
}
