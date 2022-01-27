package ru.isakaev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isakaev.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

    Comment findByText(String text);

    List<Comment> findByTextContains(String text);
}
