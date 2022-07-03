package ru.isakaev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.isakaev.model.Author;
import ru.isakaev.service.AuthorService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void shouldReturnCorrectAuthors() throws Exception {
        Author author = new Author(1L, "Author1");
        given(authorService.findAll()).willReturn(List.of(author));
        MvcResult result = mvc.perform(get("/authors"))
                                 .andExpect(status().isOk())
                                 .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        assertThat(contentAsString).isNotEmpty();
    }

    @Test
    void shouldReturnCorrectAuthor() throws Exception {
        Author author = new Author(1L, "Author1");
        given(authorService.getAuthor(1L)).willReturn(author);
        MvcResult mvcResult = mvc.perform(get("/authors/1"))
                                 .andExpect(status().isOk())
                                 .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isNotEmpty();
    }

    @Test
    void testPostAuthor() throws Exception {
        Author author = new Author(1L, "Author1");
        given(authorService.saveAuthor(author)).willReturn(author);
        mvc.perform(post("/authors")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(new ObjectMapper().writeValueAsString(author)))
           .andExpect(status().isCreated());
    }

    @Test
    void testPutAuthor() throws Exception {
        Author author = new Author(1L, "author");
        Author newAuthor = new Author(1L, "newAuthor");
        given(authorService.updateAuthor(1L, author)).willReturn(newAuthor);
        MvcResult mvcResult = mvc.perform(put("/authors/1")
                                         .contentType(MediaType.APPLICATION_JSON)
                                         .content(new ObjectMapper().writeValueAsString(author)))
                                 .andExpect(status().isOk())
                                 .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isNotEmpty();
    }

    @Test
    void testDeleteAuthor() throws Exception {
        mvc.perform(delete("/authors/1"))
                .andExpect((status().isOk()));
    }

}