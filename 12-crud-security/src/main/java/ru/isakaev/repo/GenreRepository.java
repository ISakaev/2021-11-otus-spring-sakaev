package ru.isakaev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isakaev.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>{

    Genre findByName(String name);
}
