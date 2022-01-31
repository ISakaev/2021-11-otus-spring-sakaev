package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Genre saveGenre(Genre newGenre) {
        Genre genre = repository.findByName(newGenre.getName());
        if (genre != null){
            return genre;
        }
        return repository.save(newGenre);
    }

    @Override
    public void deleteGenre(long id) {
        repository.deleteById(id);
    }
}
