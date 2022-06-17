package ru.isakaev.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.isakaev.model.Author;
import ru.isakaev.service.AuthorService;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest({AuthorController.class})
@TestPropertySource(locations = "classpath:/data-test.sql")
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @WithAnonymousUser
    public void testGetListAuthorsForUser() throws Exception {
        mvc.perform(get("/authors"))
               .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void shouldReturnCorrectAuthorsView() throws Exception {
        mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authors"))
                .andExpect(view().name("/author/listAuthors"));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirectToLoginAuthorView() throws Exception {
        mvc.perform(get("/authors/1"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void shouldReturnCorrectAuthorView() throws Exception {
        Author author = new Author(1L, "Author1");
        given(authorService.getAuthor(1L)).willReturn(author);
        mvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("/author/editAuthor"));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirectToLoginSaveAuthor() throws Exception {
        Author author = new Author(1L, "Author1");
        mvc.perform(post("/authors")
                   .with(csrf())
                   .param("id", String.valueOf(1L))
                   .param("name","Author1"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void shouldRedirectAfterSaving() throws Exception {
        Author author = new Author(1L, "Author1");
        given(authorService.getAuthor(1L)).willReturn(author);
        given(authorService.saveAuthor(author)).willReturn(author);
        mvc.perform(post("/authors")
                    .with(csrf())
                    .param("id", String.valueOf(1L))
                    .param("name","Author1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authors"))
        ;
    }

    @Test
    @WithAnonymousUser
    void shouldRedirectToLoginSaveNewAuthorGet() throws Exception {
        mvc.perform(get("/authors/new"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void shouldReturnCorrectViewDuringSaveNewAuthorGet() throws Exception {
        mvc.perform(get("/authors/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("/author/saveAuthor"));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirectToLoginSaveNewAuthorPost() throws Exception {
        Author author = new Author(1L, "Author1");
        mvc.perform(post("/authors/new")
                   .with(csrf())
                   .requestAttr("author", author))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void shouldReturnCorrectViewDuringSaveNewAuthorPost() throws Exception {
        Author author = new Author(1L, "Author1");
        given(authorService.saveAuthor(author)).willReturn(author);
        mvc.perform(post("/authors/new")
                   .with(csrf())
                   .requestAttr("author", author))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authors"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void shouldReturnBadRequestWithoutAuthoritiesDeleteAuthor() throws Exception {
        mvc.perform(get("/authors/delete/1"))
           .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"CAN_DELETE"})
    void shouldReturnCorrectViewDuringDeleteAuthor() throws Exception {
        mvc.perform(get("/authors/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authors"));
    }
}