package ru.isakaev.dao;

import org.springframework.stereotype.Repository;
import ru.isakaev.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    public BookDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from book b", Book.class);
        return query.getResultList();
    }

    @Override
    public Optional<Book> getById(int id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Book findByName(String name) {
        TypedQuery<Book> query = em.createQuery("select b from book b where b.name = :name",
                Book.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteById(int id) {
//        Book book = em.find(Book.class, id);
//        em.remove(book);
        Query query = em.createQuery("DELETE FROM book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
