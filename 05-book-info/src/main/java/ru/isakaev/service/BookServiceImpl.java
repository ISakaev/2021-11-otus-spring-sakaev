package ru.isakaev.service;

import org.springframework.stereotype.Service;
import ru.isakaev.dao.BookDao;
import ru.isakaev.model.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookDao bookDao, AuthorService authorService, GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book getBook(int id) {
        return bookDao.getById(id);
    }

    @Override
    public Book saveBook(String title, String authorName, String genreName) {
        Book book = bookDao.getAll().stream().filter(g -> (g.getTitle().equalsIgnoreCase(title)&&
                                                            g.getAuthor().getName().equalsIgnoreCase(authorName)&&
                                                            g.getGenre().getName().equalsIgnoreCase(genreName))).findAny().orElse(null);
        if (book != null){
            return book;
        }
        Book newBook = new Book(title, authorService.saveAuthor(authorName), genreService.saveGenre(genreName));
        return bookDao.save(newBook);
    }

    @Override
    public void deleteBook(int id) {
        bookDao.deleteById(id);
    }
}
