package ru.isakaev.dao;

import ru.isakaev.model.Book;
import ru.isakaev.model.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> getAll();

    Comment getById(Long id);

//    List<Comment> findByName(String name);

    List<Comment> findByBook(Book book);

    Comment save(Comment genre);

    void deleteById(Long id);
}
