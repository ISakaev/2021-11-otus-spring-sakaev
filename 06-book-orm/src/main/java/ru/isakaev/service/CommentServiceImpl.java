package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.BookDao;
import ru.isakaev.dao.CommentDao;
import ru.isakaev.model.Book;
import ru.isakaev.model.Comment;
import ru.isakaev.model.dto.CommentDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    private final BookDao bookDao;

    public CommentServiceImpl(CommentDao commentDao, BookDao bookDao) {
        this.commentDao = commentDao;
        this.bookDao = bookDao;
    }

    @Override
    public List<CommentDto> getAll() {
        List<Comment> comments = commentDao.getAll();
        return comments.stream()
                .map(c -> CommentDto.builder()
                                    .id(c.getId())
                                    .text(c.getText())
                                    .bookTitle (c.getBook().getTitle())
                                    .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Comment getComment(Long id) {
        Comment comment = commentDao.getById(id);
        return comment;
    }

    @Override
    @Transactional
    public List<CommentDto> getCommentsByBookId(Long id) {
        List<CommentDto> dtoList = commentDao.getAll().stream()
                                             .filter(comment -> comment.getBook().getId() == id)
                                             .map(c -> CommentDto.builder()
                                                                 .id(c.getId())
                                                                 .text(c.getText())
                                                                 .bookTitle (c.getBook().getTitle())
                                                                 .build())
                                             .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    @Transactional
    public Comment saveComment(String name, Long bookId) {
        Book book = bookDao.getById(bookId);
        return commentDao.save(new Comment(name, book));
    }

    @Override
    public void deleteComment(Long id) {
        commentDao.deleteById(id);
    }
}
