package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import ru.isakaev.model.Author;
import ru.isakaev.repo.AuthorRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@Sql("classpath:schema.sql")
@SpringBootTest()
class AuthorServiceImplTest {

    @MockBean
    private AuthorRepository authorRepository;

    private Author author;

    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorRepository);
        author = new Author(1L,"Первый автор");
    }

    @Test
    void shouldGetAllAuthorsFromService() {
        authorService.findAll();
        verify(authorRepository).findAll();
    }

    @Test
    void shouldGetAuthorByIdFromService() {
        authorService.getAuthor(author.getId());
        verify(authorRepository).getById(1L);
    }

    @Test
    void shouldSaveAvailableAuthor() {
        when(authorRepository.save(new Author("Первый автор"))).thenReturn(author);
        Author newAuthor = authorService.saveAuthor(new Author("Первый автор"));
        assertThat(newAuthor.getId()).isEqualTo(author.getId());
    }

    @Test
    void shouldSaveUnavailableAuthor() {
        when(authorRepository.findAll()).thenReturn(List.of(author));
        Author secondAuthor = new Author(2L, "Новый автор");
        when(authorRepository.save(new Author("Новый автор"))).thenReturn(secondAuthor);
        Author newAuthor = authorService.saveAuthor(new Author("Новый автор"));
        assertThat(newAuthor.getId()).isEqualTo(secondAuthor.getId());
    }

    @Test
    void shouldDeleteAuthor() {
        authorService.deleteAuthor(author.getId());
        verify(authorRepository).deleteById(1L);
    }

}