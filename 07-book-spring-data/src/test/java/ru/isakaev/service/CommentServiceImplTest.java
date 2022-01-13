package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.dao.CommentRepository;
import ru.isakaev.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class CommentServiceImplTest {

    @MockBean
    private CommentRepository commentRepository;

    private Comment comment;

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(commentRepository);
        comment = new Comment(1,"Комментарий");
    }

    @Test
    void shouldGetAllGenresFromComment() {
        commentService.getAll();
        verify(commentRepository).findAll();
    }

    @Test
    void shouldGetGenreByIdFromComment() {
        commentService.getComment(comment.getId());
        verify(commentRepository).findById(1);
    }

    @Test
    void shouldSaveAvailableComment() {
        when(commentRepository.findByText("Комментарий")).thenReturn(comment);
        Comment newComment= commentService.saveComment("Комментарий");
        assertThat(newComment.getId()).isEqualTo(comment.getId());
    }

    @Test
    void shouldSaveUnavailableComment() {
        when(commentRepository.findAll()).thenReturn(List.of(comment));
        Comment secondComment = new Comment(2, "Новый комментарий");
        when(commentRepository.save(new Comment("Новый комментарий"))).thenReturn(secondComment);
        Comment newComment = commentService.saveComment("Новый комментарий");
        assertThat(newComment.getId()).isEqualTo(secondComment.getId());
    }

    @Test
    void shouldDeleteComment() {
        commentService.deleteComment(comment.getId());
        verify(commentRepository).deleteById(1);
    }
}