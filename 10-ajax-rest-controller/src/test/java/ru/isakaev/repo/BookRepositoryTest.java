package ru.isakaev.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:data-test.sql")
@DataJpaTest
class BookRepositoryTest {

    private static final int EXPECTED_BOOKS_COUNT = 3;

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        Author author = new Author("New author");
        Genre genre = new Genre("New genre");
        book = new Book("New title", author, genre);
    }

    @Test
    void shouldInsertBook() {
        bookRepository.save(book);
        assertThat(bookRepository.findAll().size()).isEqualTo(EXPECTED_BOOKS_COUNT + 1);
    }

    @Test
    void shouldInsertExistBook() {
        book.setId(1L);
        bookRepository.save(book);
        assertThat(bookRepository.findAll().size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

}