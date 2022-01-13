package ru.isakaev.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Comment;
import ru.isakaev.model.Genre;
import ru.isakaev.service.AuthorServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:data-test.sql")
@DataJpaTest
class BookRepositoryTest {

    private static final int EXPECTED_BOOKS_COUNT = 3;


    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager em;

    private Book book;

    @BeforeEach
    void setUp() {
        Author author = new Author("New author");
        Genre genre = new Genre("New genre");
        Comment comment = new Comment("New comment");
        Comment comment1 = new Comment("The newest comment");
        book = new Book("New title", author, genre, List.of(comment, comment1));
    }

    @Test
    void shouldInsertBook() {
        bookRepository.save(book);
        assertThat(bookRepository.findAll().size()).isEqualTo(EXPECTED_BOOKS_COUNT + 1);
    }

    @Test
    void shouldInsertExistBook() {
        book.setId(1);
        bookRepository.save(book);
        assertThat(bookRepository.findAll().size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    void shouldGetCorrectBook(){
        Object id = em.persistAndGetId(book);
        Optional<Book> book = bookRepository.findByIdWithLazyFields((Integer) id);
        assertThat(book.get().getTitle()).isEqualTo("New title");
    }

}