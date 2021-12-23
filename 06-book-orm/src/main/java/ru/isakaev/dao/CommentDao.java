package ru.isakaev.dao;

import ru.isakaev.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

    List<Comment> getAll();

    Optional<Comment> getById(int id);

    Comment findByName(String name);

    Comment save(Comment genre);

    void deleteById(int id);
}
