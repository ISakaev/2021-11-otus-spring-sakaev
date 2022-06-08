package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.GenreRepository;
import ru.isakaev.model.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenre(int id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Genre saveGenre(String name) {
        Genre genre = genreRepository.findByName(name);
        if(genre != null){
            return genre;
        }
        return genreRepository.save(new Genre(name));
    }

    @Override
    public void deleteGenre(int id) {
        genreRepository.deleteById(id);
    }
}