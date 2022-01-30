package ru.isakaev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.isakaev.model.Author;
import ru.isakaev.service.AuthorService;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

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
    void saveAuthor() {
    }

    @Test
    void addAuthors() {
    }

    @Test
    void addAuthorsForm() {
    }

    @Test
    void deleteAuthor() {
    }
}