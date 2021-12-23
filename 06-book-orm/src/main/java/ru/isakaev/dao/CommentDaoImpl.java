package ru.isakaev.dao;

import org.springframework.stereotype.Repository;
import ru.isakaev.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext
    private final EntityManager em;

    public CommentDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = em.createQuery("select c from comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> getById(int id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public Comment findByName(String name) {
        TypedQuery<Comment> query = em.createQuery("select c from comment c where c.name = :name",
                Comment.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteById(int id) {
//        Comment comment = em.find(Comment.class, id);
//        em.remove(comment);
        Query query = em.createQuery("DELETE FROM comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

    }

}
