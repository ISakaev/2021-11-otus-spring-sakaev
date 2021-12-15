package ru.isakaev.util;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.isakaev.dao.AuthorDao;
import ru.isakaev.model.Author;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandsAuthor {

    private final AuthorDao authorDao;

    @ShellMethod(value = "Get all authors", key = {"getAuthors"})
    public void getAuthors(){
        authorDao.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get author by id", key = {"getAuthorById"})
    public String getAuthorById(@ShellOption Integer id){
        return authorDao.getById(id).toString();
    }

    @ShellMethod(value = "Save author", key = {"saveAuthor"})
    public String saveAuthor(@ShellOption String name){
        int i = authorDao.save(new Author(name));
        return "Author with id - " + i + " was saved";
    }

    @ShellMethod(value = "Delete author by id", key = {"deleteAuthorById"})
    public String deleteAuthorById(@ShellOption Integer id){
        authorDao.getAll().forEach(System.out::println);
        return "Author with id - " + id + " was deleted";
    }
}
