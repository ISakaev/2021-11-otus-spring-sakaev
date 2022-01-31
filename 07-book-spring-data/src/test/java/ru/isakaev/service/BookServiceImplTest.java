package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.dao.BookRepository;
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
    private BookRepository bookRepository;

    private Book book;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository);
        book = new Book(1,"Название книги", new Author(1,"Автор"), new Genre(1, "Жанр"),
                List.of(new Comment(1, "Первый комментарий"), new Comment(2, "Второй комментарий")));
    }

    @Test
    void shouldGetAllBooksFromService() {
        bookService.getAll();
        verify(bookRepository).findBooksWithLazyFields();
    }

    @Test
    void shouldGetBookByIdFromService() {
        bookService.getBook(book.getId());
        verify(bookRepository).findByIdWithLazyFields(1);
    }

    @Test
    void shouldSaveAvailableBook() {
        when(bookRepository.findByTitle("Название книги")).thenReturn(book);
        Book newBook = bookService.saveBook("Название книги", "Автор", "Жанр", List.of("Первый комментарий", "Второй комментарий"));
        assertThat(newBook.getId()).isEqualTo(book.getId());
    }

    @Test
    void shouldSaveUnavailableBook() {
        Author author = new Author(null, "Автор");
        Genre genre = new Genre(null, "Жанр");
        Comment comment1 = new Comment("Первый комментарий");
        Comment comment2 = new Comment("Второй комментарий");
        when(bookRepository.findByTitle("Новое название книги")).thenReturn(null);
        when(bookRepository.save(new Book("Новое название книги", author, genre, List.of(comment1,comment2)))).
                thenReturn(new Book(2, "Новое название книги", author, genre, List.of(comment1,comment2)));
        Book newBook = bookService.saveBook("Новое название книги", "Автор", "Жанр", List.of("Первый комментарий", "Второй комментарий"));
        assertThat(newBook.getId()).isEqualTo(2);
    }

    @Test
    void shouldDeleteBook() {
        bookService.deleteBook(book.getId());
        verify(bookRepository).deleteById(1);
    }
}