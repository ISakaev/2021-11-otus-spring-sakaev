package ru.isakaev.dao;

import ru.isakaev.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

    List<Comment> getAll();

    Optional<Comment> getById(Long id);

    List<Comment> findByName(String name);

    List<Comment> findByBookId(Long id);

    Comment save(Comment genre);

    void deleteById(Long id);
}
