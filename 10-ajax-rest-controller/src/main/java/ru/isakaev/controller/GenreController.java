package ru.isakaev.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.isakaev.config.exception.MyException;
import ru.isakaev.model.Genre;
import ru.isakaev.service.GenreService;

import java.util.List;

@RestController
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres(){
        return new ResponseEntity<>(
                genreService.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable("id") Long id) throws MyException {
        return new ResponseEntity<>(
                genreService.getGenre(id),
                HttpStatus.OK);
    }

    @PostMapping("/genres")
    public ResponseEntity<Genre> saveGenre(@RequestBody Genre genre) throws MyException {
        return new ResponseEntity<>(
                    genreService.saveGenre(genre),
                    HttpStatus.CREATED);
    }

    @PutMapping("/genres/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable("id") Long id, @RequestBody Genre genre) throws MyException {
        return new ResponseEntity<>(
                genreService.updateGenre(id, genre),
                HttpStatus.OK);
    }

    @DeleteMapping("/genres/{id}")
    public ResponseEntity deleteGenre(@PathVariable("id") Long id) throws MyException {
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}