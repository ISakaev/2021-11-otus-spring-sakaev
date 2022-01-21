package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.CommentDao;
import ru.isakaev.model.Comment;
import ru.isakaev.model.dto.CommentDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public List<CommentDto> getAll() {
        List<Comment> comments = commentDao.getAll();
        return comments.stream()
                .map(comment -> new CommentDto(comment.getId(), comment.getText()))
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
        List<Comment> comments = commentDao.findByBookId(id);
        List<CommentDto> dtoList = comments.stream().map(comment -> new CommentDto(comment.getId(), comment.getText())).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    @Transactional
    public Comment saveComment(String name) {
        List<Comment> comment = commentDao.findByName(name);
        if(!comment.isEmpty()){
            return comment.get(0);
        }
        return commentDao.save(new Comment(name));
    }

    @Override
    public void deleteComment(Long id) {
        commentDao.deleteById(id);
    }
}
