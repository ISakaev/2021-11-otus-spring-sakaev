package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:data-test.sql")
@DataJpaTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, GenreDaoImpl.class, CommentDaoImpl.class})
class BookDaoImplTest {

    private static final long EXPECTED_BOOKS_COUNT = 3;

    private static final long FIRST_BOOK_ID = 1;

    @Autowired
    private BookDaoImpl repositoryJPA;
    @Autowired
    private TestEntityManager em;

    @Test
    void shouldGetAllBooks() {
        int size = repositoryJPA.getAll().size();
        assertThat(size).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    void shouldGetExpectedBookById() {
        Book optionalActualBook = repositoryJPA.getById(FIRST_BOOK_ID);
        Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook)
                .isEqualToComparingFieldByField(expectedBook);
    }

    @Test
    void shouldFindExpectedBookByName(){
        List<Book> books = repositoryJPA.findByName("Первая книга");
        assertThat(books.get(0).getId()).isEqualTo(FIRST_BOOK_ID);
    }

    @Test
    void shouldInsertBook() {
        repositoryJPA.save(new Book("New title", new Author("New author"), new Genre("New genre")));
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_BOOKS_COUNT + 1);
    }

    @Test
    void shouldInsertExistBook() {
        repositoryJPA.save(new Book(1L,"New title", new Author("New author"), new Genre("New genre")));
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    void shouldDeleteAuthorById() {
        repositoryJPA.deleteById(EXPECTED_BOOKS_COUNT);
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_BOOKS_COUNT - 1);
    }

}