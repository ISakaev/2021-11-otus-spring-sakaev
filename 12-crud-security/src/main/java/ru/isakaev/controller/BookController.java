package ru.isakaev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;
import ru.isakaev.service.AuthorService;
import ru.isakaev.service.BookService;
import ru.isakaev.service.GenreService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping({"/","books"})
    public String getBooks(Model model){
        List<Book> bookList = bookService.findAll();
        model.addAttribute("books", bookList);
        return "listBooks";
    }

    @GetMapping("/books/{id}")
    public String getBook(@PathVariable("id") Integer id, Model model){
        Book book = bookService.getBook(id);
        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("book", book);
        return "editBook";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/new")
    public String saveBook(Model model){
        Book book = new Book();
        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("book", book);
        return "saveBook";
    }

    @PostMapping("/books/new")
    public String saveBookForm(@ModelAttribute("book") Book book){
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
