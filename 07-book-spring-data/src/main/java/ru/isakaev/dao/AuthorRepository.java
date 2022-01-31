package ru.isakaev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isakaev.model.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByName(String name);

    List<Author> findByNameContains(String text);
}
