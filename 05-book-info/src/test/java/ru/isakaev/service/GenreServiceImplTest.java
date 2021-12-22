package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.dao.GenreDao;
import ru.isakaev.model.Genre;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class GenreServiceImplTest {

    @MockBean
    private GenreDao genreDao;

    private Genre genre;

    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreDao);
        genre = new Genre(1,"Жанр");
    }

    @Test
    void shouldGetAllGenresFromService() {
        genreService.getAll();
        verify(genreDao).getAll();
    }

    @Test
    void shouldGetGenreByIdFromService() {
        genreService.getGenre(genre.getId());
        verify(genreDao).getById(1);
    }

    @Test
    void shouldSaveAvailableGenre() {
        when(genreDao.getAll()).thenReturn(List.of(genre));
        Genre newGenre= genreService.saveGenre("Жанр");
        assertThat(newGenre.getId()).isEqualTo(genre.getId());
    }

    @Test
    void shouldSaveUnavailableAuthor() {
        when(genreDao.getAll()).thenReturn(List.of(genre));
        Genre secondGenre = new Genre(2, "Новый жанр");
        when(genreDao.save(new Genre("Новый жанр"))).thenReturn(secondGenre);
        Genre newGenre = genreService.saveGenre("Новый жанр");
        assertThat(newGenre.getId()).isEqualTo(secondGenre.getId());
    }

    @Test
    void shouldDeleteGenre() {
        genreService.deleteGenre(genre.getId());
        verify(genreDao).deleteById(1);
    }
}