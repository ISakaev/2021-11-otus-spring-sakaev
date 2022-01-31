package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.isakaev.model.Author;
import ru.isakaev.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GenreDaoImpl.class)
class GenreDaoImplTest {

    private static final int EXPECTED_GENRES_COUNT = 4;

    @Autowired
    private GenreDaoImpl genreDao;

    @Test
    void shouldGetAllGenres() {
        int size = genreDao.getAll().size();
        assertThat(size).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @Test
    void shouldGetGenreById() {
        Genre genre = genreDao.getById(1);
        assertThat(genre.getId()).isNotNull().isEqualTo(1);
    }

    @Test
    void shouldInsertGenre() {
        genreDao.save(new Genre("New genre"));
        assertThat(genreDao.getAll().size()).isEqualTo(EXPECTED_GENRES_COUNT + 1);
    }

    @Test
    void shouldDeleteGenreById() {
        genreDao.deleteById(EXPECTED_GENRES_COUNT);
        assertThat(genreDao.getAll().size()).isEqualTo(EXPECTED_GENRES_COUNT - 1);
    }
}