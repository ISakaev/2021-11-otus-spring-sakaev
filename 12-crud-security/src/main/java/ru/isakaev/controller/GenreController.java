package ru.isakaev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.isakaev.model.Genre;
import ru.isakaev.service.GenreService;

import java.util.List;

@Controller
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String getGenres(Model model ){
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "/genre/listGenres";
    }

    @GetMapping("/genres/{id}")
    public String getGenre(@PathVariable("id") Long id, Model model){
        Genre genre = genreService.getGenre(id);
        model.addAttribute("genre", genre);
        return "/genre/editGenre.html";
    }

    @PostMapping("/genres")
    public String saveGenre(@RequestParam("id") long id, @RequestParam String name) {
        Genre genre = genreService.getGenre(id);
        genre.setName(name);
        genreService.saveGenre(genre);
        return "redirect:/genres";
    }

    @GetMapping("/genres/new")
    public String addGenre(Model model){
        Genre genre = new Genre();
        model.addAttribute("genre", genre);
        return "/genre/saveGenre.html";
    }

    @PostMapping("/genres/new")
    public String addGenreForm(@ModelAttribute("genre") Genre genre){
        genreService.saveGenre(genre);
        return "redirect:/genres";
    }

    @GetMapping("/genres/delete/{id}")
    public String deleteGenre(@PathVariable("id") Long id,  Model model){
        genreService.deleteGenre(id);
        return "redirect:/genres";
    }
}