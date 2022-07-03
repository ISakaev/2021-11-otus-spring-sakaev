package ru.isakaev.dao;

import org.springframework.stereotype.Repository;
import ru.isakaev.model.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    public BookDaoImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Получение списка всех книг
     */
    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b join fetch b.author", Book.class);
        return query.getResultList();
    }

    /**
     * Получение книги по идентификатору
     */
    @Override
    public Book getById(Long id) {
        EntityGraph<?> authorEntityGraph = em.getEntityGraph("full-book-entity-graph");
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b join fetch b.author where b.id =: id", Book.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", authorEntityGraph);
        return query.getSingleResult();
    }

    /**
     * Получение книги по названию
     */
    @Override
    public List<Book> findByName(String title) {
        EntityGraph<?> authorEntityGraph = em.getEntityGraph("full-book-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author  where b.title = :title", Book.class);
        query.setParameter("title", title);
        query.setHint("javax.persistence.fetchgraph", authorEntityGraph);
        return query.getResultList();
    }

    /**
     * Сохранение новой книги
     */
    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    /**
     * Удаление книги по идентификатору
     */
    @Override
    public void deleteById(Long id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }
}
