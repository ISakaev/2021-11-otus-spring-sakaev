package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.BookDao;
import ru.isakaev.model.Book;
import ru.isakaev.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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

//        Book book = bookDao.getAll().stream().filter(g -> (g.getTitle().equalsIgnoreCase(title)&&
//                                                            g.getAuthor().getName().equalsIgnoreCase(authorName)&&
//                                                            g.getGenre().getName().equalsIgnoreCase(genreName))).findAny().orElse(null);
//        if (book != null){
//            return book;
//        }
//        Book newBook = new Book(title, authorService.saveAuthor(authorName), genreService.saveGenre(genreName));
        List<Comment> collect = commentText.stream().map(c -> commentService.saveComment(c)).collect(Collectors.toList());
//        Book newBook = new Book(title, new Author(3, authorName), new Genre(3,genreName), collect);
        Book newBook = new Book(title, authorService.saveAuthor(authorName), genreService.saveGenre(genreName), collect);

        return bookDao.save(newBook);
    }

    @Override
    @Transactional
    public void deleteBook(int id) {
        bookDao.deleteById(id);
    }
}
