package ru.isakaev.dao;

import org.springframework.stereotype.Repository;
import ru.isakaev.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private final EntityManager em;

    public GenreDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select g from genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> getById(int id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Genre findByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from genre g where g.name = :name",
                Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() <= 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public void deleteById(int id) {
//        Genre genre = em.find(Genre.class, id);
//        em.remove(genre);
        Query query = em.createQuery("DELETE FROM genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
