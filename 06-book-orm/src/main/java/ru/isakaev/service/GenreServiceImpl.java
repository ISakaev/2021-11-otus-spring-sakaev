package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Genre getGenre(Long id) {
        return genreDao.getById(id).orElse(null);
    }

    @Override
    @Transactional
    public Genre saveGenre(String name) {
        List<Genre> genres = genreDao.findByName(name);
        if(!genres.isEmpty()){
            return genres.get(0);
        }
        return genreDao.save(new Genre(name));
    }

    @Override
    public void deleteGenre(Long id) {
        genreDao.deleteById(id);
    }
}