package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:data-test.sql")
@DataJpaTest
@Import(GenreDaoImpl.class)
class GenreDaoImplTest {

    private static final long EXPECTED_GENRE_COUNT = 4;

    private static final long FIRST_GENRE_ID = 1;

    @Autowired
    private GenreDaoImpl repositoryJPA;
    @Autowired
    private TestEntityManager em;

    @Test
    void shouldGetAllGenres() {
        int size = repositoryJPA.getAll().size();
        assertThat(size).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @Test
    void shouldGetExpectedGenreById() {
        Optional<Genre> optionalActualGenre = repositoryJPA.getById(FIRST_GENRE_ID);
        Genre expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(optionalActualGenre).isPresent().get()
                .isEqualToComparingFieldByField(expectedGenre);
    }

    @Test
    void shouldFindExpectedGenreByName(){
        List<Genre> genres = repositoryJPA.findByName("Первый жанр");
        assertThat(genres.get(0).getId()).isEqualTo(FIRST_GENRE_ID);
    }

    @Test
    void shouldInsertGenre() {
        repositoryJPA.save(new Genre("New genre"));
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_GENRE_COUNT + 1);
    }

    @Test
    void shouldInsertExistGenre() {
        repositoryJPA.save(new Genre(1L,"Самый первый жанр"));
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @Test
    void shouldDeleteGenreById() {
        repositoryJPA.deleteById(EXPECTED_GENRE_COUNT);
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_GENRE_COUNT - 1);
    }

}