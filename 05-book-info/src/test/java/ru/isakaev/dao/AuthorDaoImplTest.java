package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(AuthorDaoImpl.class)
class AuthorDaoImplTest {

    private static final int EXPECTED_AUTHORS_COUNT = 4;

    @Autowired
    private AuthorDaoImpl authorDao;

    @Test
    void shouldGetAllAuthors() {
        int size = authorDao.getAll().size();
        assertThat(size).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @Test
    void shouldGetAuthorById() {
        Author author = authorDao.getById(1);
        assertThat(author.getId()).isNotNull().isEqualTo(1);
    }

    @Test
    void shouldInsertAuthor() {
        authorDao.save(new Author("New author"));
        assertThat(authorDao.getAll().size()).isEqualTo(EXPECTED_AUTHORS_COUNT + 1);
    }

    @Test
    void shouldDeleteAuthorById() {
        authorDao.deleteById(EXPECTED_AUTHORS_COUNT);
        assertThat(authorDao.getAll().size()).isEqualTo(EXPECTED_AUTHORS_COUNT - 1);
    }
}