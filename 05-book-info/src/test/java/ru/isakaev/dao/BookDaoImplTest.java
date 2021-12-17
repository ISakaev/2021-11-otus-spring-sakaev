package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, GenreDaoImpl.class})
class BookDaoImplTest {

    private static final int EXPECTED_BOOKS_COUNT = 3;

    @Autowired
    private AuthorDaoImpl authorDao;
    @Autowired
    private GenreDaoImpl genreDao;

//    private BookDaoImpl bookDao;
//
//    BookDaoImplTest(BookDaoImpl bookDao) {
//        this.bookDao = bookDao;
//    }

    @Test
    void shouldReturnListBooks() {
//        int size = bookDao.getAll().size();
//        assertThat(size).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    void getById() {
    }

    @Test
    void shouldInsertBook() {
    }

    @Test
    void shouldDeleteBookById() {
    }
}