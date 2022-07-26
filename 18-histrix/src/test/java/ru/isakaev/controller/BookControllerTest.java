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
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;
import ru.isakaev.service.AuthorService;
import ru.isakaev.service.BookService;
import ru.isakaev.service.GenreService;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:/data-test.sql")
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Test
    @WithAnonymousUser
    void shouldReturnCorrectBooksView() throws Exception {
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("listBooks"));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirectedToLoginBookView() throws Exception {
        Book book = new Book(1L, "New Book", new Author("Author1"), new Genre("Genre1"));
        mvc.perform(get("/books/1"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void shouldReturnCorrectBookView() throws Exception {
        Book book = new Book(1L, "New Book", new Author("Author1"), new Genre("Genre1"));
        given(bookService.getBook(1L)).willReturn(book);
        given(authorService.findAll()).willReturn(Collections.EMPTY_LIST);
        given(genreService.findAll()).willReturn(Collections.EMPTY_LIST);
        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(view().name("editBook"));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirectToLoginPostBook() throws Exception {
        Book book = new Book(1L, "New Book", new Author("Author1"), new Genre("Genre1"));
        mvc.perform(post("/books")
                   .with(csrf())
                   .requestAttr("book", book))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void shouldRedirectAfterBookSaving() throws Exception {
        Book book = new Book(1L, "New Book", new Author("Author1"), new Genre("Genre1"));
        mvc.perform(post("/books")
                    .with(csrf())
                    .requestAttr("book", book))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirectToLoginSaveNewBookGet() throws Exception {
        mvc.perform(get("/books/new"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void shouldReturnCorrectViewDuringSaveNewBookGet() throws Exception {
        given(authorService.findAll()).willReturn(Collections.EMPTY_LIST);
        given(genreService.findAll()).willReturn(Collections.EMPTY_LIST);
        mvc.perform(get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(view().name("saveBook"));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirectToLoginSaveNewBookPost() throws Exception {
        Book book = new Book(1L, "New Book", new Author("Author1"), new Genre("Genre1"));
        mvc.perform(post("/books/new")
                   .with(csrf())
                   .requestAttr("book", book))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void shouldReturnCorrectViewDuringSaveNewBookPost() throws Exception {
        Book book = new Book(1L, "New Book", new Author("Author1"), new Genre("Genre1"));
        given(bookService.saveBook(book)).willReturn(book);
        mvc.perform(post("/books/new")
                    .with(csrf())
                    .requestAttr("book", book))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void shouldReturnBadRequestWithoutAuthoritiesDeleteBook() throws Exception {
        mvc.perform(get("/books/delete/1"))
           .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"CAN_DELETE"})
    void shouldReturnCorrectViewDuringDeleteBook() throws Exception {
        mvc.perform(get("/books/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

}