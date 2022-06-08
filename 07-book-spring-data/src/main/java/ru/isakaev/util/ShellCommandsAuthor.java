package ru.isakaev.util;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.isakaev.model.Author;
import ru.isakaev.service.AuthorService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandsAuthor {

    private final AuthorService authorService;

    @ShellMethod(value = "Get all authors", key = {"gas", "getAuthors"})
    public void getAuthors(){
        authorService.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get author by id", key = {"ga", "getAuthor"})
    public String getAuthor(@ShellOption Integer id){
        Author author = authorService.getAuthor(id);
        if(author != null){
            return author.toString();
        }
        return "Author with id " + id + " was not found";
    }

    @ShellMethod(value = "Get author by name contain text", key = {"gac", "getAuthorByNameContainContext"})
    public void getAuthorByNameContainContext(@ShellOption String text){
        List<Author> authors = authorService.findByNameContainText(text);
        if (authors.isEmpty()){
            System.out.println("Author with name contain - " + text + " was not found");
        }else {
            authors.forEach(System.out::println);
        }
    }

    @ShellMethod(value = "Save author", key = {"sa", "saveAuthor"})
    public String saveAuthor(@ShellOption String name){
        Author author = authorService.saveAuthor(name);
        return "Author " + author.toString() + " was saved";
    }

    @ShellMethod(value = "Delete author by id", key = {"da", "deleteAuthor"})
    public String deleteAuthor(@ShellOption Integer id){
        authorService.deleteAuthor(id);
        return "Author with id - " + id + " was deleted";
    }
}
