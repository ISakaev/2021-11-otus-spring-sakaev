package ru.isakaev.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.isakaev.model.Author;
import ru.isakaev.service.AuthorService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;


    @Test
    void shouldReturnCorrectAuthorsView() throws Exception {
        mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authors"))
                .andExpect(view().name("/author/listAuthors"));
    }

    @Test
    void shouldReturnCorrectAuthorView() throws Exception {
        Author author = new Author(1L, "Author1");
        given(authorService.getAuthor(1L)).willReturn(author);
        mvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("/author/editAuthor"));
    }

    @Test
    void shouldRedirectAfterSaving() throws Exception {
        Author author = new Author(1L, "Author1");
        given(authorService.getAuthor(1L)).willReturn(author);
        given(authorService.saveAuthor(author)).willReturn(author);
        mvc.perform(post("/authors")
                    .param("id", String.valueOf(1L))
                    .param("name","Author1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authors"));
    }

    @Test
    void shouldReturnCorrectViewDuringSaveNewAuthorGet() throws Exception {
        mvc.perform(get("/authors/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("/author/saveAuthor"));
    }

    @Test
    void shouldReturnCorrectViewDuringSaveNewAuthorPost() throws Exception {
        Author author = new Author(1L, "Author1");
        given(authorService.saveAuthor(author)).willReturn(author);
        mvc.perform(post("/authors/new").requestAttr("author", author))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authors"));
    }

    @Test
    void shouldReturnCorrectViewDuringDeleteAuthor() throws Exception {
        mvc.perform(get("/authors/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authors"));
    }
}