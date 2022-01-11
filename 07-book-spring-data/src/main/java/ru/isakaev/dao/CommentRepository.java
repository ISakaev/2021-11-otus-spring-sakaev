package ru.isakaev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isakaev.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Comment findByText(String text);
}
