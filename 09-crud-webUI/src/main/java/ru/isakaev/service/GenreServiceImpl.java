package ru.isakaev.service;

import org.springframework.stereotype.Service;
import ru.isakaev.model.Genre;
import ru.isakaev.repo.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Genre> findAll() {
        return repository.findAll();
    }

    @Override
    public Genre getGenre(long id) {
        return repository.getById(id);
    }

    @Override
    public Genre saveGenre(Genre genre) {
        return repository.save(genre);
    }

    @Override
    public void deleteGenre(long id) {
        repository.deleteById(id);
    }
}
