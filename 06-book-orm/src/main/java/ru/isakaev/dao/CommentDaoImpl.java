package ru.isakaev.dao;

import org.springframework.stereotype.Repository;
import ru.isakaev.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext
    private final EntityManager em;

    public CommentDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public Comment getById(Long id) {
        TypedQuery<Comment> query = em.createQuery("select distinct c from Comment c " +
                "join fetch c.book b " +
                "join fetch b.genre " +
                "join fetch b.author " +
                "where c.id =: id", Comment.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Comment> findByName(String text) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c " +
                "join fetch c.book b " +
                "join fetch b.genre " +
                "join fetch b.author " +
                "where c.text = :text", Comment.class);
        query.setParameter("text", text);
        return query.getResultList();
    }

    @Override
    public List<Comment> findByBookId(Long id) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c " +
                " where c.book.id = :id", Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteById(Long id) {
        Comment comment = em.find(Comment.class, id);
        em.remove(comment);
    }

}
