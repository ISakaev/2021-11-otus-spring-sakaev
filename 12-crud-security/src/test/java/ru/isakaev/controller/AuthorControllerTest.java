package ru.isakaev.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.isakaev.model.Author;
import ru.isakaev.repo.UserRepository;
import ru.isakaev.service.AuthorService;
import ru.isakaev.service.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
//@WebMvcTest({AuthorController.class, UserService.class})
@WebMvcTest({AuthorController.class})
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    public void testGetListAuthorsForUser() throws Exception {
        mvc.perform(get("/authors"))
               .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "user", authorities = {"ROLE_USER"})
    @Test
    public void testPostAuthorsForUser() throws Exception {
        mvc.perform(post("/authors"))
           .andExpect(status().isOk());
    }


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