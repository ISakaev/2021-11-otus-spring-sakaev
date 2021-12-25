package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.CommentDao;
import ru.isakaev.model.Comment;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getComment(int id) {
        Comment comment = commentDao.getById(id).orElse(null);
        return comment;
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
    @Transactional
    public void deleteComment(int id) {
        commentDao.deleteById(id);
    }
}
