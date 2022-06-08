package ru.isakaev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isakaev.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    Genre findByName(String name);
}
