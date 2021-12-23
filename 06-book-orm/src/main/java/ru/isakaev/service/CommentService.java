package ru.isakaev.service;

import ru.isakaev.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAll();

    Comment getAuthor(int id);

    Comment saveAuthor(String name);

    void deleteAuthor(int id);
}
