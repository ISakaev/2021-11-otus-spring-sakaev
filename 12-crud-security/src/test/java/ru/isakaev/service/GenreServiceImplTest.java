package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.model.Genre;
import ru.isakaev.repo.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest()
class GenreServiceImplTest {

    @MockBean
    private GenreRepository genreRepository;

    private Genre genre;

    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreRepository);
        genre = new Genre(1L,"Жанр");
    }

    @Test
    void shouldGetAllGenresFromService() {
        genreService.findAll();
        verify(genreRepository).findAll();
    }

    @Test
    void shouldGetGenreByIdFromService() {
        genreService.getGenre(genre.getId());
        verify(genreRepository).getById(1L);
    }

    @Test
    void shouldSaveAvailableGenre() {
        when(genreRepository.save(new Genre("Жанр"))).thenReturn(genre);
        Genre newGenre= genreService.saveGenre(new Genre("Жанр"));
        assertThat(newGenre.getId()).isEqualTo(genre.getId());
    }

    @Test
    void shouldSaveUnavailableAuthor() {
        when(genreRepository.findAll()).thenReturn(List.of(genre));
        Genre secondGenre = new Genre(2L, "Новый жанр");
        when(genreRepository.save(new Genre("Новый жанр"))).thenReturn(secondGenre);
        Genre newGenre = genreService.saveGenre(new Genre("Новый жанр"));
        assertThat(newGenre.getId()).isEqualTo(secondGenre.getId());
    }

    @Test
    void shouldDeleteGenre() {
        genreService.deleteGenre(genre.getId());
        verify(genreRepository).deleteById(1L);
    }
}