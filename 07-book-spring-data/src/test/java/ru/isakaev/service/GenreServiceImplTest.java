package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.dao.GenreRepository;
import ru.isakaev.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class GenreServiceImplTest {

    @MockBean
    private GenreRepository genreRepository;

    private Genre genre;

    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreRepository);
        genre = new Genre(1,"Жанр");
    }

    @Test
    void shouldGetAllGenresFromService() {
        genreService.getAll();
        verify(genreRepository).findAll();
    }

    @Test
    void shouldGetGenreByIdFromService() {
        genreService.getGenre(genre.getId());
        verify(genreRepository).findById(1);
    }

    @Test
    void shouldSaveAvailableGenre() {
        when(genreRepository.findByName("Жанр")).thenReturn(genre);
        Genre newGenre= genreService.saveGenre("Жанр");
        assertThat(newGenre.getId()).isEqualTo(genre.getId());
    }

    @Test
    void shouldSaveUnavailableAuthor() {
        when(genreRepository.findAll()).thenReturn(List.of(genre));
        Genre secondGenre = new Genre(2, "Новый жанр");
        when(genreRepository.save(new Genre("Новый жанр"))).thenReturn(secondGenre);
        Genre newGenre = genreService.saveGenre("Новый жанр");
        assertThat(newGenre.getId()).isEqualTo(secondGenre.getId());
    }

    @Test
    void shouldDeleteGenre() {
        genreService.deleteGenre(genre.getId());
        verify(genreRepository).deleteById(1);
    }
}