package ru.isakaev.service;

import ru.isakaev.config.exception.MyException;
import ru.isakaev.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();

    Genre getGenre(Long id) throws MyException;

    Genre saveGenre(Genre genre) throws MyException;

    void deleteGenre(Long id) throws MyException;

    Genre updateGenre(Long id, Genre genre) throws MyException;

}