package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.dao.BookDao;
import ru.isakaev.dao.CommentDao;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Comment;
import ru.isakaev.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class CommentServiceImplTest {

    @MockBean
    private CommentDao commentDao;
    @MockBean
    private BookDao bookDao;

    private Comment comment;

    private Book book;

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(commentDao, bookDao);
        comment = new Comment(1L,"Комментарий");
        book = new Book(1L,"Название книги", new Author(1L,"Автор"), new Genre(1L, "Жанр"), List.of(comment));
    }

    @Test
    void shouldGetAllGenresFromComment() {
        commentService.getAll();
        verify(commentDao).getAll();
    }

    @Test
    void shouldGetGenreByIdFromComment() {
        commentService.getComment(comment.getId());
        verify(commentDao).getById(1L);
    }

    @Test
    void shouldSaveAvailableComment() {
        when(bookDao.getById(1L)).thenReturn(book);
        when(commentDao.save( new Comment("Комментарий"))).thenReturn(comment);
        Comment newComment= commentService.saveComment("Комментарий", 1L);
        assertThat(newComment.getId()).isEqualTo(comment.getId());
    }

    @Test
    void shouldDeleteComment() {
        commentService.deleteComment(comment.getId());
        verify(commentDao).deleteById(1L);
    }
}