package ru.isakaev.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:data-test.sql")
@DataJpaTest
class AuthorRepositoryTest {

    private TestEntityManager entityManager;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryTest(TestEntityManager entityManager, AuthorRepository authorRepository) {
        this.entityManager = entityManager;
        this.authorRepository = authorRepository;
    }

    @Test
    public void shouldCorrectSaveAuthor(){
        entityManager.persistAndGetId(new Author("Test Author"));
        Author testAuthor = authorRepository.findByName("Test Author");

        assertThat(testAuthor.getId()).isNotNull();
    }

    @Test
    public void shouldGetCorrectAuthorContainText(){
        entityManager.persist(new Author("Test Author"));
        List<Author> authors = authorRepository.findByNameContains("uthor");
        assertThat(authors.size()).isNotEqualTo(0);
    }
}