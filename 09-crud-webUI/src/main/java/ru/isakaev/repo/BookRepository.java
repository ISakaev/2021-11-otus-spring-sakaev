package ru.isakaev.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.isakaev.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>{

    Book findByTitle(String name);

//    @EntityGraph(value = "book-author-entity-graph")
//    @Query("SELECT b FROM Book b JOIN FETCH b.comments")
//    List<Book> findBooksWithLazyFields();

//    @EntityGraph(value = "book-author-entity-graph")
//    @Query("SELECT b FROM Book b JOIN FETCH b.comments where b.id =:id")
//    Optional<Book> findByIdWithLazyFields(@Param("id") Integer id);
}
