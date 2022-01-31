package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.dao.AuthorRepository;
import ru.isakaev.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class AuthorServiceImplTest {

    @MockBean
    private AuthorRepository authorRepository;

    private Author author;

    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorRepository);
        author = new Author(1,"Первый автор");
    }

    @Test
    void shouldGetAllAuthorsFromService() {
        authorService.getAll();
        verify(authorRepository).findAll();
    }

    @Test
    void shouldGetAuthorByIdFromService() {
        authorService.getAuthor(author.getId());
        verify(authorRepository).findById(1);
    }

    @Test
    void shouldSaveAvailableAuthor() {
        when(authorRepository.findByName("Первый автор")).thenReturn(author);
        Author newAuthor = authorService.saveAuthor("Первый автор");
        assertThat(newAuthor.getId()).isEqualTo(author.getId());
    }

    @Test
    void shouldSaveUnavailableAuthor() {
        when(authorRepository.findAll()).thenReturn(List.of(author));
        Author secondAuthor = new Author(2, "Новый автор");
        when(authorRepository.save(new Author("Новый автор"))).thenReturn(secondAuthor);
        Author newAuthor = authorService.saveAuthor("Новый автор");
        assertThat(newAuthor.getId()).isEqualTo(secondAuthor.getId());
    }

    @Test
    void shouldDeleteAuthor() {
        authorService.deleteAuthor(author.getId());
        verify(authorRepository).deleteById(1);
    }
}