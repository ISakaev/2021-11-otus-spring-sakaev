package ru.isakaev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isakaev.model.Author;

import java.util.List;

public interface AuthorRepository  extends JpaRepository<Author, Long>{

    Author findByName(String name);

    List<Author> findByNameContains(String text);


}
