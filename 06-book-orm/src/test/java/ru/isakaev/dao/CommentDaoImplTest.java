package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Book;
import ru.isakaev.model.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:data-test.sql")
@DataJpaTest
@Import(CommentDaoImpl.class)
class CommentDaoImplTest {

    private static final long EXPECTED_COMMENT_COUNT = 5;

    private static final long FIRST_COMMENT_ID = 1;

    @Autowired
    private CommentDaoImpl repositoryJPA;
    @Autowired
    private TestEntityManager em;

    @Test
    void shouldGetAllComments() {
        int size = repositoryJPA.getAll().size();
        assertThat(size).isEqualTo(EXPECTED_COMMENT_COUNT);
    }

    @Test
    void shouldGetExpectedCommentById() {
        Comment optionalActualComment = repositoryJPA.getById(FIRST_COMMENT_ID);
        Comment expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isEqualToComparingFieldByField(expectedComment);
    }

    @Test
    void shouldInsertComment() {
        repositoryJPA.save(new Comment("New comment"));
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_COMMENT_COUNT + 1);
    }

    @Test
    void shouldInsertExistComment() {
        repositoryJPA.save(new Comment(1L,"Первый коментарий"));
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_COMMENT_COUNT);
    }

    @Test
    void shouldDeleteCommentById() {
        repositoryJPA.deleteById(EXPECTED_COMMENT_COUNT);
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_COMMENT_COUNT - 1);
    }
}