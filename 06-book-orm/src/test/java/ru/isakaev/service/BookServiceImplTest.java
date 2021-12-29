package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.dao.BookDao;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Comment;
import ru.isakaev.model.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @MockBean
    private CommentService commentService;

    private Book book;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookDao, authorService, genreService, commentService);
        book = new Book(1,"Название книги", new Author(1,"Автор"), new Genre(1, "Жанр"),
                List.of(new Comment(1, "Первый комментарий"), new Comment(2, "Второй комментарий")));
    }

    @Test
    void shouldGetAllBooksFromService() {
        bookService.getAll();
        verify(bookDao).getAll();
    }

    @Test
    void shouldGetBookByIdFromService() {
        bookService.getBook(book.getId());
        verify(bookDao).getById(1);
    }

    @Test
    void shouldSaveAvailableBook() {
        when(bookDao.findByName("Название книги")).thenReturn(List.of(book));
        when(commentService.saveComment("Первый комментарий")).thenReturn(new Comment(1, "Первый комментарий"));
        when(commentService.saveComment("Второй комментарий")).thenReturn(new Comment(2, "Второй комментарий"));

        Book newBook = bookService.saveBook("Название книги", "Автор", "Жанр", List.of("Первый комментарий", "Второй комментарий"));
        assertThat(newBook.getId()).isEqualTo(book.getId());
    }

    @Test
    void shouldSaveUnavailableBook() {
        Author author = new Author(1, "Автор");
        Genre genre = new Genre(1, "Жанр");
        Comment comment1 = new Comment(1, "Первый комментарий");
        Comment comment2 = new Comment(2, "Второй комментарий");
        when(bookDao.findByName("Новое название книги")).thenReturn(Collections.EMPTY_LIST);
        when(authorService.saveAuthor("Автор")).thenReturn(author);
        when(genreService.saveGenre("Жанр")).thenReturn(genre);
        when(bookDao.save(new Book("Новое название книги", author, genre, List.of(comment1,comment2)))).
                thenReturn(new Book(2, "Новое название книги", author, genre, List.of(comment1,comment2)));
        Book newBook = bookService.saveBook("Новое название книги", "Автор", "Жанр", List.of("Первый комментарий", "Второй комментарий"));
        assertThat(newBook.getId()).isEqualTo(2);
    }

    @Test
    void shouldDeleteBook() {
        bookService.deleteBook(book.getId());
        verify(bookDao).deleteById(1);
    }
}