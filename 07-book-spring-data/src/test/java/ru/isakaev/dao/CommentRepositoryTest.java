package ru.isakaev.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:data-test.sql")
@DataJpaTest
class CommentRepositoryTest {

    private TestEntityManager entityManager;

    private CommentRepository commentRepository;

    @Autowired
    public CommentRepositoryTest(TestEntityManager entityManager, CommentRepository commentRepository) {
        this.entityManager = entityManager;
        this.commentRepository = commentRepository;
    }

    @Test
    public void shouldCorrectSaveComment(){
        Object o = entityManager.persistAndGetId(new Comment("Test Comment"));
        Comment testComment = commentRepository.findByText("Test Comment");

        assertThat(testComment.getId()).isNotNull();
    }

    @Test
    public void shouldGetCorrectCommentContainText(){
        Object o = entityManager.persistAndGetId(new Comment("Test Comment"));
        List<Comment> testComment = commentRepository.findByTextContains("Comment");
        assertThat(testComment.size()).isNotEqualTo(0);
    }

}