package ru.isakaev.util;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.isakaev.model.Book;
import ru.isakaev.service.BookService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandsBook {

    private final BookService bookService;

    @ShellMethod(value = "Get all books", key = {"gbs", "getBooks"})
    public void getBooks(){
        bookService.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get book by id", key = {"gb", "getBook"})
    public String getBook(@ShellOption Integer id){
        return bookService.getBook(id).toString();
    }

    @ShellMethod(value = "Save book", key = {"sb", "saveBook"})
    public String saveBook(@ShellOption String name,
                           @ShellOption String author,
                           @ShellOption String genre){
        Book book = bookService.saveBook(name, author, genre);
        return "Book - " + book + " was saved";
    }

    @ShellMethod(value = "Delete book", key = {"db", "deleteBookById"})
    public String deleteBook(@ShellOption Integer id){
        bookService.getAll().forEach(System.out::println);
        return "Application end";
    }
}
