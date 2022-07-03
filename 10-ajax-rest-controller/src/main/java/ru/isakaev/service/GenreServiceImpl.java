package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.config.exception.MyException;
import ru.isakaev.model.Genre;
import ru.isakaev.repo.GenreRepository;

import java.util.List;
import java.util.Optional;

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
    public Genre getGenre(Long id) throws MyException {
        Optional<Genre> optionalGenre = repository.findById(id);
        if (optionalGenre.isEmpty()) {
            throw new MyException(String.format("Genre with id=%s not found", id));
        }
        return optionalGenre.get();
    }

    @Override
    @Transactional
    public Genre saveGenre(Genre newGenre) throws MyException {
        Genre genre = repository.findByName(newGenre.getName());
        if (genre != null){
            throw new MyException(String.format("Genre with name=%s is exist", newGenre.getName()));
        }
        return repository.save(newGenre);
    }

    @Override
    @Transactional
    public Genre updateGenre(Long id, Genre newGenre) throws MyException {
        Optional<Genre> optionalGenre = repository.findById(id);
        if (optionalGenre.isEmpty()){
            throw new MyException(String.format("Genre with id=%s not found", id));
        }
        Genre genre = optionalGenre.get();
        genre.setName(newGenre.getName());
        return repository.save(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(Long id) throws MyException {
        Optional<Genre> optionalGenre = repository.findById(id);
        if (optionalGenre.isEmpty()){
            throw new MyException(String.format("Genre with id=%s not found", id));
        }
        repository.deleteById(id);
    }
}
