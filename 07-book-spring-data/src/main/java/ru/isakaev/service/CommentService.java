package ru.isakaev.service;

import ru.isakaev.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAll();

    List<Comment> findByTextContains(String text);

    Comment getComment(int id);

    Comment saveComment(String name);

    void deleteComment(int id);
}
