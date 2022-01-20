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

    private Book book;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookDao);
        book = new Book(1L,"Название книги", new Author(1L,"Автор"), new Genre(1L, "Жанр"));
    }

    @Test
    void shouldGetAllBooksFromService() {
        bookService.getAll();
        verify(bookDao).getAll();
    }

    @Test
    void shouldGetBookByIdFromService() {
        bookService.getBook(book.getId());
        verify(bookDao).getById(1L);
    }

    @Test
    void shouldSaveAvailableBook() {
        when(bookDao.findByName("Название книги")).thenReturn(List.of(book));
        Book newBook = bookService.saveBook("Название книги", "Автор", "Жанр");
        assertThat(newBook.getId()).isEqualTo(book.getId());
    }

    @Test
    void shouldSaveUnavailableBook() {
        Author author = new Author(null, "Автор");
        Genre genre = new Genre(null, "Жанр");
        Comment comment1 = new Comment("Первый комментарий");
        Comment comment2 = new Comment("Второй комментарий");
        when(bookDao.findByName("Новое название книги")).thenReturn(Collections.EMPTY_LIST);
        when(bookDao.save(new Book("Новое название книги", author, genre))).
                thenReturn(new Book(2L, "Новое название книги", author, genre));
        Book newBook = bookService.saveBook("Новое название книги", "Автор", "Жанр");
        assertThat(newBook.getId()).isEqualTo(2);
    }

    @Test
    void shouldDeleteBook() {
        bookService.deleteBook(book.getId());
        verify(bookDao).deleteById(1L);
    }
}