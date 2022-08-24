package ru.isakaev.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(fallbackMethod = "defaultGenresFindAll")
    public List<Genre> findAll() {
        return repository.findAll();
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultGenresGet")
    public Genre getGenre(long id) {
        return repository.getById(id);
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "defaultGenresSave")
    public Genre saveGenre(Genre newGenre) {
        Genre genre = repository.findByName(newGenre.getName());
        if (genre != null){
            return genre;
        }
        return repository.save(newGenre);
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultDeleteGenre")
    public void deleteGenre(long id) {
        repository.deleteById(id);
    }

    public List<Genre> defaultGenresFindAll() {
        return List.of(new Genre(-1L,"Hystrix"));
    }

    public Genre defaultGenresGet(long id) {
        return new Genre(-1L,"Hystrix");
    }

    public Genre defaultGenresSave(Genre genre) {
        return new Genre(-1L,"Hystrix");
    }

    public void defaultDeleteGenre(long id) {
    }
}
