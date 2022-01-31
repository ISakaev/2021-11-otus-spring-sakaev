package ru.isakaev.service;

import ru.isakaev.model.Comment;
import ru.isakaev.model.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getAll();

    Comment getComment(Long id);

    List<CommentDto> getCommentsByBookId(Long id);

    Comment saveComment(String name, Long bookId);

    void deleteComment(Long id);
}
