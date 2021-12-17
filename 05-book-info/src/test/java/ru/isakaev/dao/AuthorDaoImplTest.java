package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(AuthorDaoImpl.class)
class AuthorDaoImplTest {

    private static final int EXPECTED_BOOKS_COUNT = 3;

    @Autowired
    private AuthorDaoImpl authorDao;

    @Test
    void getAll() {
        int size = authorDao.getAll().size();
        assertThat(size).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    void getById() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }
}