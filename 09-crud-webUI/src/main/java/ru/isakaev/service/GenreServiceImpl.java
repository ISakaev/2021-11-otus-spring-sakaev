package ru.isakaev.service;

import org.springframework.stereotype.Service;
import ru.isakaev.model.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public Genre getGenre(int id) {
        return null;
    }

    @Override
    public Genre saveGenre(String name) {
        return null;
    }

    @Override
    public void deleteGenre(int id) {

    }
}
