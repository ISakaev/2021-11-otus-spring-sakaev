package ru.isakaev.dao;

import org.springframework.stereotype.Repository;
import ru.isakaev.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    public AuthorDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select a from author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Author findByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from author a where a.name = :name",
                Author.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public Optional<Author> getById(int id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Author save(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public void deleteById(int id) {
//        Author author = em.find(Author.class, id);
//        em.remove(author);
        Query query = em.createQuery("DELETE FROM author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
