package ru.isakaev.util;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.isakaev.dao.BookDao;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandsBook {

    private final BookDao bookDao;

    @ShellMethod(value = "Get all books", key = {"getBooks"})
    public String getBooks(){
        bookDao.getAll().forEach(System.out::println);
        return "Application end";
    }

    @ShellMethod(value = "Get all books", key = {"getBookById"})
    public String getBookById(@ShellOption Integer id){
        return bookDao.getById(id).toString();
    }

    @ShellMethod(value = "Get all books", key = {"saveBook"})
    public String saveBook(@ShellOption Integer id,
                           @ShellOption String name,
                           @ShellOption String author,
                           @ShellOption String genre){
        Book book = new Book(id,name, new Author(id, author), new Genre(id,genre));
        bookDao.save(book);
        return "Book - " + book.toString() + " was saved";
    }

    @ShellMethod(value = "Get all books", key = {"deleteBookById"})
    public String deleteBookById(@ShellOption Integer id){
        bookDao.getAll().forEach(System.out::println);
        return "Application end";
    }
}
