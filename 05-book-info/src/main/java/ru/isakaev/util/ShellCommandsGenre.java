package ru.isakaev.util;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.isakaev.model.Genre;
import ru.isakaev.service.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandsGenre {

    private final GenreService genreService;

    @ShellMethod(value = "Get all genres", key = {"ggs", "getGenres"})
    public void getGenres(){
        genreService.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get genre by id", key = {"gg", "getGenre"})
    public String getGenre(@ShellOption Integer id){
        return genreService.getGenre(id).toString();
    }

    @ShellMethod(value = "Save genre", key = {"sg", "saveGenre"})
    public String saveGenre(@ShellOption String name){
        Genre genre = genreService.saveGenre(name);
        return "Genre " + genre.toString() + " was saved";
    }

    @ShellMethod(value = "Delete genre by id", key = {"dg", "deleteGenreById"})
    public String deleteGenre(@ShellOption Integer id){
        genreService.deleteGenre(id);
        return "Genre with id - " + id + " was deleted";
    }
}
