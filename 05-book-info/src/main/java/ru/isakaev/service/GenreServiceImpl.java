package ru.isakaev.service;

import org.springframework.stereotype.Service;
import ru.isakaev.dao.GenreDao;

import ru.isakaev.model.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Genre getGenre(int id) {
        return genreDao.getById(id);
    }

    @Override
    public Genre saveGenre(String name) {
        Genre genre = genreDao.getAll().stream().filter(g -> g.getName().equalsIgnoreCase(name)).findAny().orElse(null);
        if (genre != null){
            return genre;
        }
        return genreDao.save(new Genre(name));
    }

    @Override
    public void deleteGenre(int id) {
        genreDao.deleteById(id);
    }
}
