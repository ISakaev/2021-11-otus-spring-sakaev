package ru.isakaev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;
import ru.isakaev.service.BookService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    private Book book;

    private ObjectMapper ob;

    @BeforeEach
    void setUp() {
        ob = new ObjectMapper();
        book = new Book(1L, "New Book", new Author("Author1"), new Genre("Genre1"));
    }

    @Test
    void shouldReturnBooksList() throws Exception {
        when(bookService.findAll()).thenReturn(List.of(book));
        MvcResult mvcResult = mvc.perform(get("/books"))
                                 .andExpect(status().isOk())
                                 .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString.contains(book.getTitle())).isTrue();
    }

    @Test
    void shouldReturnCorrectBook() throws Exception {
        given(bookService.getBook(1L)).willReturn(book);
        MvcResult mvcResult = mvc.perform(get("/books/1"))
                                 .andExpect(status().isOk())
                                 .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Book jsonBook = ob.readValue(contentAsString, Book.class);
        assertThat(jsonBook).isEqualTo(book);
    }

    @Test
    void testPostBook() throws Exception {
        given(bookService.saveBook(book)).willReturn(book);
        MvcResult mvcResult = mvc.perform(post("/books")
                                         .contentType(MediaType.APPLICATION_JSON)
                                         .content(new ObjectMapper().writeValueAsString(book)))
                                 .andExpect(status().isCreated())
                                 .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Book jsonBook = ob.readValue(contentAsString, Book.class);
        assertThat(jsonBook).isEqualTo(book);
    }

    @Test
    void testPutBook() throws Exception {
        Book putBook = new Book(1L, "Put Book", new Author("Author1"), new Genre("Genre1"));
        given(bookService.updateBook(1L, book)).willReturn(putBook);
        MvcResult mvcResult = mvc.perform(put("/books/1")
                                         .contentType(MediaType.APPLICATION_JSON)
                                         .content(new ObjectMapper().writeValueAsString(book)))
                                 .andExpect(status().isOk())
                                 .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Book jsonBook = ob.readValue(contentAsString, Book.class);
        assertThat(jsonBook.getTitle()).isEqualTo("Put Book");
    }

    @Test
    void testDeleteBook() throws Exception {
        mvc.perform(delete("/books/1"))
           .andExpect((status().isOk()));
    }

}