package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.isakaev.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorDaoImpl.class)
class AuthorDaoImplTest {

    private static final int EXPECTED_AUTHORS_COUNT = 1;
    private static final int FIRST_STUDENT_ID = 1;

    private AuthorDaoImpl repositoryJPA;

    private TestEntityManager em;

    public AuthorDaoImplTest(AuthorDaoImpl repositoryJPA, TestEntityManager em) {
        this.repositoryJPA = repositoryJPA;
        this.em = em;
    }

    @Test
    void shouldGetAllAuthors() {
        int size = repositoryJPA.getAll().size();
        assertThat(size).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @Test
    void shouldGetAuthorById() {
        Author author = repositoryJPA.getById(1).orElse(null);
        if (author != null){
            assertThat(author.getId()).isNotNull().isEqualTo(1);
        }
    }

    @Test
    void shouldInsertAuthor() {
        repositoryJPA.save(new Author("New author"));
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_AUTHORS_COUNT + 1);
    }

    @Test
    void shouldDeleteAuthorById() {
        repositoryJPA.deleteById(EXPECTED_AUTHORS_COUNT);
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_AUTHORS_COUNT - 1);
    }

//    @Test
//    void shouldGetExpectedAuthorById() {
//        Optional<Author> optionalActualAuthor = repositoryJPA.getById(FIRST_STUDENT_ID);
//        Author expectedStudent = em.find(Author.class, FIRST_STUDENT_ID);
//        assertThat(optionalActualAuthor).isPresent().get()
//                .isEqualToComparingFieldByField(expectedStudent);
//    }
}