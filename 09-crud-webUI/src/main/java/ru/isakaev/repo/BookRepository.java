package ru.isakaev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isakaev.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

    Book findByTitle(String name);
}
