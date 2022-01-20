package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Comment;

import java.util.List;
import java.util.Optional;

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
        Optional<Comment> optionalActualComment = repositoryJPA.getById(FIRST_COMMENT_ID);
        Comment expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .isEqualToComparingFieldByField(expectedComment);
    }

    @Test
    void shouldFindExpectedCommentByName(){
        List<Comment> comments = repositoryJPA.findByName("Первый коментарий");
        assertThat(comments.get(0).getId()).isEqualTo(FIRST_COMMENT_ID);
    }

    @Test
    void shouldInsertComment() {
        repositoryJPA.save(new Comment("New comment"));
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_COMMENT_COUNT + 1);
    }

//    @Test
//    void shouldInsertExistComment() {
//        repositoryJPA.save(new Comment(1,"Самый первый коментарий"));
//        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_COMMENT_COUNT);
//    }

    @Test
    void shouldDeleteCommentById() {
        repositoryJPA.deleteById(EXPECTED_COMMENT_COUNT);
        assertThat(repositoryJPA.getAll().size()).isEqualTo(EXPECTED_COMMENT_COUNT - 1);
    }
}