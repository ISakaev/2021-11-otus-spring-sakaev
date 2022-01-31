package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, GenreDaoImpl.class})
class BookDaoImplTest {

    private static final int EXPECTED_BOOKS_COUNT = 3;

    @Autowired
    private BookDaoImpl bookDao;

    @Test
    void shouldReturnListBooks() {
        List<Book> books = bookDao.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(book -> !book.getTitle().equals(""))
                .allMatch(book -> book.getAuthor() != null)
                .allMatch(book -> book.getGenre() != null);
        books.forEach(System.out::println);
    }

    @Test
    void shouldGetBookById() {
        Book book = bookDao.getById(1);
        assertThat(book.getId()).isNotNull().isEqualTo(1);
    }

    @Test
    void shouldInsertBook() {
        Book newBook = new Book("Четвертая книга", new Author(3,"Третий автор"), new Genre(2,"Второй жанр"));
        Book save = bookDao.save(newBook);
        assertThat(save.getId()).isEqualTo(EXPECTED_BOOKS_COUNT + 1);
    }

    @Test
    void shouldDeleteBookById() {
        bookDao.deleteById(bookDao.getAll().size());
        List<Book> bookAfterDelete = bookDao.getAll();
        assertThat(bookAfterDelete).hasSize(EXPECTED_BOOKS_COUNT - 1);
    }
}