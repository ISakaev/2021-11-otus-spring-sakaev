package ru.isakaev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.isakaev.dao.AuthorDao;
import ru.isakaev.model.Author;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class AuthorServiceImplTest {

    @MockBean
    private AuthorDao authorDao;

    private Author author;

    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorDao);
        author = new Author(1,"Автор");
    }

    @Test
    void getAll() {
        authorDao.getAll();
        verify(authorDao).getAll();
    }

    @Test
    void getAuthor() {
        authorService.getAuthor(author.getId());
        verify(authorDao).getById(1);
    }

    @Test
    void saveAvailableAuthor() {
        when(authorDao.getAll()).thenReturn(List.of(author));
        Author newAuthor = authorService.saveAuthor("Автор");
        assertThat(newAuthor.getId()).isEqualTo(author.getId());
    }

    @Test
    void saveUnavailableAuthor() {
        when(authorDao.getAll()).thenReturn(List.of(author));
        Author secondAuthor = new Author(2, "Новый автор");
        when(authorDao.save(new Author("Новый автор"))).thenReturn(secondAuthor);
        Author newAuthor = authorService.saveAuthor("Новый автор");
        assertThat(newAuthor.getId()).isEqualTo(secondAuthor.getId());
    }

    @Test
    void deleteAuthor() {
        authorService.deleteAuthor(author.getId());
        verify(authorDao).deleteById(1);
    }
}