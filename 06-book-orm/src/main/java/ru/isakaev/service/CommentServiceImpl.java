package ru.isakaev.service;

import ru.isakaev.dao.CommentDao;
import ru.isakaev.model.Comment;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public Comment getAuthor(int id) {
        return null;
    }

    @Override
    public Comment saveAuthor(String name) {
        return null;
    }

    @Override
    public void deleteAuthor(int id) {

    }
}
