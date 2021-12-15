package ru.isakaev.util;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.isakaev.dao.AuthorDao;
import ru.isakaev.dao.GenreDao;
import ru.isakaev.model.Author;
import ru.isakaev.model.Genre;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandsGenre {

    private final GenreDao genreDao;

    @ShellMethod(value = "Get all genres", key = {"getGenres"})
    public void getGenres(){
        genreDao.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get genre by id", key = {"getGenreById"})
    public String getGenreById(@ShellOption Integer id){
        return genreDao.getById(id).toString();
    }

    @ShellMethod(value = "Save genre", key = {"saveGenre"})
    public String saveGenre(@ShellOption String name){
        int i = genreDao.save(new Genre(name));
        return "Genre with id - " + i + " was saved";
    }

    @ShellMethod(value = "Delete genre by id", key = {"deleteGenreById"})
    public String deleteGenreById(@ShellOption Integer id){
        genreDao.deleteById(id);
        return "Genre with id - " + id + " was deleted";
    }
}
