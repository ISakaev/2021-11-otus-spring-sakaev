package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.dao.CommentDao;
import ru.isakaev.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class CommentServiceImplTest {

    @MockBean
    private CommentDao commentDao;

    private Comment comment;

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(commentDao);
        comment = new Comment(1,"Комментарий");
    }

    @Test
    void shouldGetAllGenresFromComment() {
        commentService.getAll();
        verify(commentDao).getAll();
    }

    @Test
    void shouldGetGenreByIdFromComment() {
        commentService.getComment(comment.getId());
        verify(commentDao).getById(1);
    }

    @Test
    void shouldSaveAvailableComment() {
        when(commentDao.findByName("Комментарий")).thenReturn(List.of(comment));
        Comment newComment= commentService.saveComment("Комментарий");
        assertThat(newComment.getId()).isEqualTo(comment.getId());
    }

    @Test
    void shouldSaveUnavailableComment() {
        when(commentDao.getAll()).thenReturn(List.of(comment));
        Comment secondComment = new Comment(2, "Новый комментарий");
        when(commentDao.save(new Comment("Новый комментарий"))).thenReturn(secondComment);
        Comment newComment = commentService.saveComment("Новый комментарий");
        assertThat(newComment.getId()).isEqualTo(secondComment.getId());
    }

    @Test
    void shouldDeleteComment() {
        commentService.deleteComment(comment.getId());
        verify(commentDao).deleteById(1);
    }
}