package ru.isakaev.dao;

import org.springframework.stereotype.Repository;
import ru.isakaev.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    public AuthorDaoImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Получение списка всех авторов
    */
    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    /**
     * Получение автора по имени
     */
    @Override
    public List<Author> findByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name",
                Author.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    /**
     * Получение автора по идентификатору
     */
    @Override
    public Optional<Author> getById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    /**
     * Сохраняем нового автора
     */
    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    /**
     * Удаляем автора по идентификатору
     */
    @Override
    public void deleteById(Long id) {
        Author author = em.find(Author.class, id);
        em.remove(author);
    }

}
