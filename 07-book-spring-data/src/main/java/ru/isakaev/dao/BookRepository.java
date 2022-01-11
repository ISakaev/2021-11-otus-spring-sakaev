package ru.isakaev.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.isakaev.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByTitle(String name);

    @EntityGraph(value = "book-author-entity-graph")
    @Query("SELECT b FROM Book b JOIN FETCH b.comments")
//    @Query("SELECT b FROM Book b")
    List<Book> findBooksWithLazyFields();

    // List comment by bookId with like "комментарий"
}
