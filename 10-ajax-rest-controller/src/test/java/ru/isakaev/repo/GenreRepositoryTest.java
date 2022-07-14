package ru.isakaev.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:data-test.sql")
@DataJpaTest
class GenreRepositoryTest {

    private TestEntityManager entityManager;

    private GenreRepository genreRepository;

    @Autowired
    public GenreRepositoryTest(TestEntityManager entityManager, GenreRepository genreRepository) {
        this.entityManager = entityManager;
        this.genreRepository = genreRepository;
    }

    @Test
    public void shouldCorrectSaveGenre(){
        entityManager.persistAndGetId(new Genre("Test Genre"));
        Genre testGenre = genreRepository.findByName("Test Genre");

        assertThat(testGenre.getId()).isNotNull();
    }
}