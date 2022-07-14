package ru.isakaev.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.isakaev.model.Author;
import ru.isakaev.service.AuthorService;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAuthors(){
        return new ResponseEntity<>(
                authorService.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable("id") Long id) throws MyException {
        return new ResponseEntity<>(
                authorService.getAuthor(id),
                HttpStatus.OK);
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) throws MyException {
        return new ResponseEntity<>(
                authorService.saveAuthor(author),
                HttpStatus.CREATED);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") Long id, @RequestBody Author updateAuthor)
            throws MyException {
        return new ResponseEntity<>(
                authorService.updateAuthor(id, updateAuthor),
                HttpStatus.OK);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id) throws MyException {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}