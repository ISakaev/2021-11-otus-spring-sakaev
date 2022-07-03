package ru.isakaev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isakaev.model.Author;

import java.util.List;

@Repository
public interface AuthorRepository  extends JpaRepository<Author, Long>{

    Author findByName(String name);

    List<Author> findByNameContains(String text);

}
