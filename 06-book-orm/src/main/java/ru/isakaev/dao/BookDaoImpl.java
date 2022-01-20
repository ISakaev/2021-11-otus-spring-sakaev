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
//        TypedQuery<Book> query = em.createQuery("select distinct b from Book b join fetch b.comments", Book.class);
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b", Book.class);
//        query.setHint("javax.persistence.fetchgraph", authorEntityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findByName(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title = :title",
                Book.class);
        query.setParameter("title", title);
        return query.getResultList();
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
    public void deleteById(Long id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }
}
