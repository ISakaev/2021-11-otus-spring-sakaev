package ru.isakaev.service;

import org.springframework.stereotype.Service;
import ru.isakaev.model.Comment;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public List<Comment> findByTextContains(String text) {
        return null;
    }

    @Override
    public Comment getComment(int id) {
        return null;
    }

    @Override
    public Comment saveComment(String name) {
        return null;
    }

    @Override
    public void deleteComment(int id) {

    }
}
