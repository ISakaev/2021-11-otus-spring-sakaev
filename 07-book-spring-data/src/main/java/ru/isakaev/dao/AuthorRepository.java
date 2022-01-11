package ru.isakaev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isakaev.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByName(String name);
}
