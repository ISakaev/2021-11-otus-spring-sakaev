package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.dao.BookDao;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class BookServiceImplTest {

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private Book book;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookDao, authorService, genreService);
        book = new Book(1,"Название книги", new Author(1,"Автор"), new Genre(1, "Жанр"));
    }

    @Test
    void getAll() {
        bookService.getAll();
        verify(bookDao).getAll();
    }

    @Test
    void getBook() {
        bookService.getBook(book.getId());
        verify(bookDao).getById(1);
    }

    @Test
    void saveAvailableBook() {
        when(bookDao.getAll()).thenReturn(List.of(book));
        Book newBook = bookService.saveBook("Название книги", "Автор", "Жанр");
        assertThat(newBook.getId()).isEqualTo(book.getId());
    }

    @Test
    void saveUnavailableBook() {
        Author author = new Author(1, "Автор");
        Genre genre = new Genre(1, "Жанр");
        when(bookDao.getAll()).thenReturn(List.of(book));
        when(authorService.saveAuthor("Автор")).thenReturn(author);
        when(genreService.saveGenre("Жанр")).thenReturn(genre);
        when(bookDao.save(new Book("Новое название книги", author, genre))).thenReturn(new Book(2, "Новое название книги", author, genre));
        Book newBook = bookService.saveBook("Новое название книги", "Автор", "Жанр");
        assertThat(newBook.getId()).isEqualTo(2);
    }

    @Test
    void deleteBook() {
        bookService.deleteBook(book.getId());
        verify(bookDao).deleteById(1);
    }

}