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
    public List<Genre> getAll() {
        return repository.findAll();
    }

    @Override
    public Genre getGenre(Long id) {
        return repository.getById(id);
    }

    @Override
    public Genre saveGenre(String name) {
        return null;
    }

    @Override
    public void deleteGenre(int id) {

    }
}
