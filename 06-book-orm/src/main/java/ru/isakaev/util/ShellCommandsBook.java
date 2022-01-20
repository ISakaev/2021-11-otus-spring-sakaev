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
    public String getBook(@ShellOption Long id){
        Book book = bookService.getBook(id);
        if(book != null){
            return book.toString();
        }
        return "Book with id " + id + " was not found";
    }

    @ShellMethod(value = "Save book", key = {"sb", "saveBook"})
    public String saveBook(@ShellOption String title,
                           @ShellOption String author,
//                           @ShellOption(defaultValue = "") List<String> comments,  // --comments "Новый комментарий, Еще один комментарий"
                           @ShellOption String genre){
        Book book = bookService.saveBook(title, author, genre);
        return "Book - " + book + " was saved";
    }

    @ShellMethod(value = "Delete book", key = {"db", "deleteBookById"})
    public String deleteBook(@ShellOption Long id){
        bookService.deleteBook(id);
        return "Book - " + id + " was deleted";
    }
}//sb --title NameBook --author NameAuthor --genre NameGenre --comments "Новый комментарий, Еще один комментарий"
