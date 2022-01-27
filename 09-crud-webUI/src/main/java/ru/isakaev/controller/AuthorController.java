package ru.isakaev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.isakaev.model.Author;
import ru.isakaev.service.AuthorService;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String getAuthors(Model model){
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "listAuthors";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable("id") Long id,  Model model){
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        return "editAuthor";
    }

    @PutMapping("/author")
    public String getAuthor(@RequestParam("name") String name){
        authorService.saveAuthor(new Author(name));
        return "redirect:/authors";
    }

    @PostMapping("/author")
    public String saveAuthor(@RequestParam("id") int id, @RequestParam String name) {
        Author author = authorService.getAuthor((long) id);
        author.setName(name);
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/author/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id,  Model model){
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
