package ru.isakaev.dao;

import org.springframework.stereotype.Repository;
import ru.isakaev.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private final EntityManager em;

    public GenreDaoImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Получение списка всех жанров
     */
    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    /**
     * Получение жанра по идентификатору
     */
    @Override
    public Optional<Genre> getById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    /**
     * Получение жанра по названию
     */
    @Override
    public List<Genre> findByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name",
                Genre.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    /**
     * Сохранение нового жанра
     */
    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    /**
     * Удаление жанра по идентификатору
     */
    @Override
    public void deleteById(Long id) {
        Genre genre = em.find(Genre.class, id);
        em.remove(genre);
    }
}
