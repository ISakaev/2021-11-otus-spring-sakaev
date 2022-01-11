package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.CommentRepository;
import ru.isakaev.model.Comment;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getComment(int id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        return comment;
    }

    @Override
    @Transactional
    public Comment saveComment(String text) {
        Comment comment = commentRepository.findByText(text);
        if(comment != null){
            return comment;
        }
        return commentRepository.save(new Comment(text));
    }

    @Override
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }
}
