package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:data-test.sql")
@DataJpaTest
@Import(AuthorDaoImpl.class)
class AuthorDaoImplTest {

    private static final long EXPECTED_AUTHORS_COUNT = 4;

    private static final long FIRST_AUTHOR_ID = 1;

    @Autowired
    private AuthorDaoImpl repositoryJPA;
    @Autowired
    private TestEntityManager em;

    @Test
    void shouldGetAllAuthors() {
        int size = repositoryJPA.getAll().size();
        assertThat(size).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @Test
    void shouldGetExpectedAuthorById() {
        Optional<Author> optionalActualAuthor = repositoryJPA.getById(FIRST_AUTHOR_ID);
        Author expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .isEqualToComparingFieldByField(expectedAuthor);
    }

    @Test
    void shouldFindExpectedAuthorByName(){
        List<Author> authors = repositoryJPA.findByName("Первый автор");
        assertThat(authors.get(0).getId()).isEqualTo(FIRST_AUTHOR_ID);
    }

    @Test
    void shouldInsertAuthor() {
        repositoryJPA.save(new Author("New author"));
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_AUTHORS_COUNT + 1);
    }

    @Test
    void shouldInsertExistAuthor() {
        repositoryJPA.save(new Author(1L,"Самый первый автор"));
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @Test
    void shouldDeleteAuthorById() {
        repositoryJPA.deleteById(EXPECTED_AUTHORS_COUNT);
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_AUTHORS_COUNT - 1);
    }
}