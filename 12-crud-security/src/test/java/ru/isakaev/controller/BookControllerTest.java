package ru.isakaev.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;
import ru.isakaev.service.AuthorService;
import ru.isakaev.service.BookService;
import ru.isakaev.service.GenreService;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(BookController.class)
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
    void shouldReturnCorrectBooksView() throws Exception {
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("listBooks"));
    }

    @Test
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
    void shouldRedirectAfterBookSaving() throws Exception {
        Book book = new Book(1L, "New Book", new Author("Author1"), new Genre("Genre1"));
        mvc.perform(post("/books")
                .requestAttr("book", book))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
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
    void shouldReturnCorrectViewDuringSaveNewBookPost() throws Exception {
        Book book = new Book(1L, "New Book", new Author("Author1"), new Genre("Genre1"));
        given(bookService.saveBook(book)).willReturn(book);
        mvc.perform(post("/books/new").requestAttr("book", book))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    void shouldReturnCorrectViewDuringDeleteAuthor() throws Exception {
        mvc.perform(get("/books/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

}