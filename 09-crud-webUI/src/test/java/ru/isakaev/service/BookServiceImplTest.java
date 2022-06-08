package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;
import ru.isakaev.repo.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest()
class BookServiceImplTest {

    @MockBean
    private BookRepository bookRepository;

    private Book book;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository);
        book = new Book(1L,"Название книги", new Author(1L,"Автор"), new Genre(1L, "Жанр"));
    }

    @Test
    void shouldGetAllBooksFromService() {
        bookService.findAll();
        verify(bookRepository).findAll();
    }

    @Test
    void shouldGetBookByIdFromService() {
        bookService.getBook(book.getId());
        verify(bookRepository).getById(1L);
    }

    @Test
    void shouldSaveAvailableBook() {
        when(bookRepository.findByTitle("Название книги")).thenReturn(book);
        Book newBook = bookService.saveBook(new Book("Название книги", new Author("Автор"), new Genre("Жанр")));
        assertThat(newBook.getId()).isEqualTo(book.getId());
    }

    @Test
    void shouldSaveUnavailableBook() {
        Author author = new Author(null, "Автор");
        Genre genre = new Genre(null, "Жанр");
        Book savedBook = new Book("Название книги", author, genre);
        when(bookRepository.findByTitle("Новое название книги")).thenReturn(null);
        when(bookRepository.save(savedBook)).
                thenReturn(new Book(2L, "Новое название книги", author, genre));
        Book newBook = bookService.saveBook(savedBook);
        assertThat(newBook.getId()).isEqualTo(2);
    }

    @Test
    void shouldDeleteBook() {
        when(bookRepository.getById(book.getId())).thenReturn(book);
        bookService.deleteBook(book.getId());
        verify(bookRepository).delete(book);
    }
}