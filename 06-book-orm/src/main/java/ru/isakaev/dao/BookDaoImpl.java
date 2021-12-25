package ru.isakaev.dao;

import org.springframework.stereotype.Repository;
import ru.isakaev.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    public BookDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> getAll() {
//        EntityGraph<?> authorEntityGraph = em.getEntityGraph("book-author-entity-graph");
//        EntityGraph<?> genreEntityGraph = em.getEntityGraph("book-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b join fetch b.comment", Book.class);
//        query.setHint("javax.persistence.fetchgraph", authorEntityGraph);
//        query.setHint("javax.persistence.fetchgraph", genreEntityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> getById(int id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Book findByName(String title) {

        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title = :title",
                Book.class);
        query.setParameter("title", title);
        return query.getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteById(int id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }
}
